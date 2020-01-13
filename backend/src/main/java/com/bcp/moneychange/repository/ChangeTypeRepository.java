package com.bcp.moneychange.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bcp.moneychange.entity.ChangeType;

@Repository
public interface ChangeTypeRepository extends JpaRepository<ChangeType, Long>  {

    @Query(
            value = "SELECT * FROM CHANGE_TYPE t where t.SOURCE_CURRENCY = :sourceCurrency AND t.DESTINATION_CURRENCY = :destinationCurrency",
            nativeQuery=true
    )
    Optional<ChangeType> findBySourceAndDestinationCurrency(@Param("sourceCurrency") String sourceCurrency,
                                                            @Param("destinationCurrency") String destinationCurrency);
}