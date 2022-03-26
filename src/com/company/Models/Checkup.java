package com.company.Models;

import java.util.List;

public class Checkup extends Medical_procedure{
    protected Affection diagnostic;
    protected List<Treatment> prescription;
    protected List<String> speciality_referral;

}
