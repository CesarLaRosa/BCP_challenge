package com.bcp.moneychange.controller;

import com.bcp.moneychange.dto.ChangeMoneyResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.bcp.moneychange.dto.ChangeMoneySourceDto;
import com.bcp.moneychange.service.MoneyService;
import org.springframework.web.context.request.async.DeferredResult;
import rx.Observable;

@RestController
@RequestMapping( value = "/money")
public class MoneyController {
	
	@Autowired
	private MoneyService moneyService;

	@RequestMapping(value = "/change")
	@PreAuthorize("hasAuthority('ADMIN_USER') or hasAuthority('STANDARD_USER')")
	public DeferredResult<ChangeMoneyResponseDto> login(@RequestBody ChangeMoneySourceDto body) {
		Observable<ChangeMoneyResponseDto> observableResponse = this.moneyService.change(body);
		DeferredResult<ChangeMoneyResponseDto> deffered = new DeferredResult<>();
		observableResponse.subscribe(m -> deffered.setResult(m), e -> deffered.setErrorResult(e));
		return deffered;
	}
}