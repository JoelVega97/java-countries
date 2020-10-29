package com.lambdawork.countries.Repos;

import com.lambdawork.countries.Models.Countries;
import org.springframework.data.repository.CrudRepository;

public interface CountriesRepo extends CrudRepository<Countries, Long> {
}
