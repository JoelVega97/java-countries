package com.lambdawork.countries.Controllers;


import com.lambdawork.countries.Models.Countries;
import com.lambdawork.countries.Repos.CountriesRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CountryController {
    @Autowired
    CountriesRepo countriesRepo;

    private List<Countries> filterCountries(List<Countries> myList, CheckCountry tester){
        List<Countries> testList = new ArrayList<>();

        for (Countries c: myList) {
            if (tester.test(c)) {
                testList.add(c);
            }
        }
        return testList;
    }

    //http://localhost:2019/name/all

    @GetMapping(value = "/name/all", produces = "application/json")
    public ResponseEntity<?> listAllCountries() {
        List<Countries> myList = new ArrayList<>();
        countriesRepo.findAll().iterator().forEachRemaining(myList::add);

        return new ResponseEntity<>(myList, HttpStatus.OK);
    }

    //http://localhost:2019/names/start/u

    @GetMapping(value = "/names/start/{letter}", produces = "application/json")
    public ResponseEntity<?> findCountryByName(@PathVariable char letter){
        List<Countries> myList = new ArrayList<>();
        countriesRepo.findAll().iterator().forEachRemaining(myList::add);

        List<Countries> rtnList = filterCountries(myList, c -> c.getName().charAt(0) == letter);
        return new ResponseEntity<>(rtnList, HttpStatus.OK);
    }

    //http://localhost:2019/population/total

    //http://localhost:2019/population/min

    //http://localhost:2019/population/max

}
