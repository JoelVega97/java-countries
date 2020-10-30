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
    @GetMapping(value = "population/total", produces = "application/json")
    public ResponseEntity<?> findTotalPopulation(){
        List<Countries> myList = new ArrayList<>();
        countriesRepo.findAll().iterator().forEachRemaining(myList::add);

        long total = 0;
        for (Countries c : myList){
            total += c.getPopulation();
        }
        System.out.println("The Total Population is " + total);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    //http://localhost:2019/population/min
    @GetMapping(value = "/population/min", produces = "application/json")
    public ResponseEntity<?> findSmallestPopulation(){
        List<Countries> myList = new ArrayList<>();
        countriesRepo.findAll().iterator().forEachRemaining(myList::add);

        myList.sort((c1,c2) -> (int)(c1.getPopulation() - c2.getPopulation()));
        return new ResponseEntity<>(myList.get(0), HttpStatus.OK);
    }

    //http://localhost:2019/population/max

    @GetMapping(value = "/population/max", produces = "application/json")
    public ResponseEntity<?> findLargestPopulation(){
        List<Countries> myList = new ArrayList<>();
        countriesRepo.findAll().iterator().forEachRemaining(myList::add);

        myList.sort((c1,c2) -> (int)(c2.getPopulation() - c1.getPopulation()));
        return new ResponseEntity<>(myList.get(0), HttpStatus.OK);
    }
}
