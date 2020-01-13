import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { config } from './../../config';
import { map } from 'rxjs/operators';
import { User } from '../model/user';
import { HttpClient, HttpHeaders } from '@angular/common/http';

@Injectable({ providedIn: 'root' })
export class AuthenticationService {
    private currentUserSubject: BehaviorSubject<User>;
    public currentUser: Observable<User>;

    constructor(private http: HttpClient) {
        this.currentUserSubject = new BehaviorSubject<User>(JSON.parse(localStorage.getItem('currentUser')));
        this.currentUser = this.currentUserSubject.asObservable();
    }

    public get currentUserValue(): User {
        return this.currentUserSubject.value;
    }

    getCurrentUser() {
      return localStorage.getItem('currentUser');
    }

    login(grantType: string, username: string, password: string) {
        const headers = new HttpHeaders({ Authorization: 'Basic ' + btoa('testjwtclientid' + ':' + 'XY7kmzoNzl100') });

        return this.http.post<any>(`${config.apiUrl}/oauth/token?grant_type=` + grantType + '&username='
                                  + username + '&password=' + password, {}, { headers } )
            .pipe(map(user => {
                // store user details and basic auth credentials in local storage to keep user logged in between page refreshes
                user.authdata = window.btoa('testjwtclientid' + ':' + 'XY7kmzoNzl100');
                localStorage.setItem('currentUser', JSON.stringify(user));
                this.currentUserSubject.next(user);
                return user;
            }));
    }

    logout() {
        // remove user from local storage to log user out
        localStorage.removeItem('currentUser');
        this.currentUserSubject.next(null);
    }
}
