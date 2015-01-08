/*
 * Copyright (c) Erik Malmstrom-Partridge 2014. Do not distribute, edit, or modify in anyway, without direct written consent of Erik Malmstrom-Partridge.
 */

/** (c) Erik Malmstrom-Partridge 2014
 This code is provided as-is without any warranty
 This source code or its compiled program may not be redistributed in any way
 * */
package com.takeoffsim.models.people;

import com.takeoffsim.models.economics.Company;
import lombok.Data;
import org.jetbrains.annotations.NotNull;
import org.jfairy.Fairy;
import org.jfairy.producer.person.Person;
import org.jfairy.producer.person.PersonProperties;

import java.io.Serializable;

/**
 * @author Erik
 */


public
@Data
class Employee implements Serializable {

    private int salary;
    private String firstName;
    private String lastName;
    private int age;
    private Company company;

    public Employee(int sal, String fName, String last, int years, Company corp) {
        this.salary = sal;
        this.firstName = fName;
        this.lastName = last;
        this.age = years;
        this.company = corp;
    }

    @NotNull
    public static Employee createEmployee(Company company) {
        Fairy fairy = Fairy.create();
        Person person = fairy.person(PersonProperties.minAge(19));
        return new Employee(0, person.firstName(), person.lastName(), person.age(), company);
    }

    @NotNull
    @Override
    public String toString() {
        return "Employee{" +
                "salary=" + salary +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                ", company=" + company +
                '}';
    }
}
