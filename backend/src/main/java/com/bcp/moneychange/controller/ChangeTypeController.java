package com.bcp.moneychange.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.bcp.moneychange.entity.ChangeType;
import com.bcp.moneychange.exception.ResourceNotFoundException;
import com.bcp.moneychange.repository.ChangeTypeRepository;

@RestController
@RequestMapping("/changeType")
public class ChangeTypeController {

    @Autowired
    private ChangeTypeRepository changeTypeRepository;

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ADMIN_USER') or hasAuthority('STANDARD_USER')")
    public List<ChangeType> getAllChangeTypes() {
        return changeTypeRepository.findAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN_USER') or hasAuthority('STANDARD_USER')")
    public ResponseEntity< ChangeType > getChangeTypeById(@PathVariable(value = "id") Long changeTypeId) {
        return ResponseEntity.ok().body(changeTypeRepository.findOne(changeTypeId));
    }

    @GetMapping("/{sourceCurrency}/{destinationCurrency}")
    @PreAuthorize("hasAuthority('ADMIN_USER') or hasAuthority('STANDARD_USER')")
    public ResponseEntity< ChangeType > getChangeTypeByCurrency(@PathVariable(value = "sourceCurrency") String sourceCurrency,
                                                                @PathVariable(value = "destinationCurrency") String destinationCurrency)
            throws ResourceNotFoundException {
        ChangeType searchSource = new ChangeType();
        searchSource.setSourceCurrency(sourceCurrency);
        searchSource.setDestinationCurrency(destinationCurrency);

        ChangeType changeType = changeTypeRepository.findBySourceAndDestinationCurrency(sourceCurrency,destinationCurrency)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found for this combination :: " + sourceCurrency + ","+ destinationCurrency));
        return ResponseEntity.ok().body(changeType);
    }

    @PostMapping()
    @PreAuthorize("hasAuthority('ADMIN_USER') or hasAuthority('STANDARD_USER')")
    public ChangeType createChangeType(@Valid @RequestBody ChangeType changeType) {
        return changeTypeRepository.save(changeType);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN_USER') or hasAuthority('STANDARD_USER')")
    public ResponseEntity < ChangeType > updateChangeType(@PathVariable(value = "id") Long changeTypeId,
                                                          @Valid @RequestBody ChangeType changeTypeDetails) throws ResourceNotFoundException {
        ChangeType changeType = changeTypeRepository.findOne(changeTypeId);

        if (changeType == null) new ResourceNotFoundException("ChangeType not found for this id :: " + changeTypeId);

        changeType.setSourceCurrency(changeTypeDetails.getSourceCurrency());
        changeType.setDestinationCurrency(changeTypeDetails.getDestinationCurrency());
        changeType.setChangeTypeValue(changeTypeDetails.getChangeTypeValue());
        final ChangeType updatedChangeType = changeTypeRepository.save(changeType);
        return ResponseEntity.ok(updatedChangeType);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN_USER') or hasAuthority('STANDARD_USER')")
    public Map< String, Boolean > deleteChangeType(@PathVariable(value = "id") Long changeTypeId)
            throws ResourceNotFoundException {
        ChangeType changeType = changeTypeRepository.findOne(changeTypeId);
        if (changeType == null) new ResourceNotFoundException("ChangeType not found for this id :: " + changeTypeId);

        changeTypeRepository.delete(changeType);
        Map < String, Boolean > response = new HashMap< >();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}
