/*
 * Copyright (c) Erik Malmstrom-Partridge 2014. Do not distribute, edit, or modify in anyway, without direct written consent of Erik Malmstrom-Partridge.
 */

/*
 * (c) Erik Malmstrom-com.erikpartridge 2013, 2014.
*/

package com.takeoffsim.models.economics;

import com.takeoffsim.models.airline.Airline;
import com.takeoffsim.models.world.Country;
import lombok.extern.apachecommons.CommonsLog;
import org.apache.commons.logging.Log;
import org.apache.commons.math3.random.MersenneTwister;
import org.apache.commons.math3.random.RandomGenerator;
import org.jetbrains.annotations.NotNull;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

import java.io.Serializable;
import java.util.ArrayList;
import com.takeoffsim.airport.Gate;

@CommonsLog
public class Company implements Serializable, Entity {
    static final long serialVersionUID = -606077089004L;

    private static final RandomGenerator RNG = new MersenneTwister();

    @NotNull
    private ArrayList<Stock> holdings = new ArrayList<Stock>();

    private final double FACTOR = (RNG.nextGaussian() / 2) + 1;

    private Stock corporateStock;

    private ArrayList<Stock> shares = new ArrayList<>();

    private boolean isSubsidiary;

    private String ceo;

    private String headquarters;

    private Money valuation;

    private Country country;

    private Money earnings;

    private Money costs;

    private Money cash;

    private double earningsPerShare;

    private double dividend;

    private ArrayList<Airline> subsidiaries;

    private ArrayList<Contract> contracts;

    private ArrayList<Gate> gatesOwned;

    private String name;

    private boolean isPrivate;

    public static Log getLog() {
        return log;
    }

    public Company(){
        super();
    }

    public Company(@NotNull ArrayList<Stock> holdings, long numShares, Stock corporateStock, boolean isSubsidiary, String ceo, String headquarters, Money valuation, Country country, Money costs, double earningsPerShare, double dividend, ArrayList<Airline> subsidiaries, Money cash, Money earnings) {
        this.holdings = holdings;
        shares = new ArrayList<>();
        shares.add(new Stock(this,numShares));
        this.corporateStock = corporateStock;
        this.isSubsidiary = isSubsidiary;
        this.ceo = ceo;
        this.headquarters = headquarters;
        this.valuation = valuation;
        this.country = country;
        this.costs = costs;
        this.earningsPerShare = earningsPerShare;
        this.dividend = dividend;
        this.subsidiaries = subsidiaries;
        this.cash = cash;
        this.earnings = earnings;
        this.setContracts(new ArrayList<>());
        this.gatesOwned = new ArrayList<>();
    }

    public void setSharesOfCorporate(int shares) {
        getLog().trace("Setting shares:" + shares);
        getCorporateStock().setShares(getCorporateStock().getShares() + shares);
    }

    public void addToHoldings(Stock s) {
        getHoldings().add(s);
    }

    @Override
    public String toString() {
        return "Company{" +
                "name='" + getName() + '\'' +
                ", holdings=" + getHoldings() +
                ", corporateStock=" + getCorporateStock() +
                ", isPrivate=" + isPrivate() +
                ", numShares=" + getNumShares() +
                ", isSubsidiary=" + isSubsidiary() +
                ", ceo='" + getCeo() + '\'' +
                ", headquarters='" + getHeadquarters() + '\'' +
                ", country=" + getCountry() +
                ", earnings=" + getEarnings() +
                ", costs=" + getCosts() +
                ", profits=" + getCash() +
                ", earningsPerShare=" + getEarningsPerShare() +
                ", dividend=" + getDividend() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Company)) return false;

        Company company = (Company) o;

        if (Double.compare(company.dividend, dividend) != 0) return false;
        if (Double.compare(company.earningsPerShare, earningsPerShare) != 0) return false;
        if (isSubsidiary != company.isSubsidiary) return false;
        if (shares.size() != company.shares.size()) return false;
        if (cash != null ? !cash.equals(company.cash) : company.cash != null) return false;
        if (ceo != null ? !ceo.equals(company.ceo) : company.ceo != null) return false;
        if (corporateStock != null ? !corporateStock.equals(company.corporateStock) : company.corporateStock != null)
            return false;
        if (costs != null ? !costs.equals(company.costs) : company.costs != null) return false;
        if (country != null ? !country.equals(company.country) : company.country != null) return false;
        if (earnings != null ? !earnings.equals(company.earnings) : company.earnings != null) return false;
        if (headquarters != null ? !headquarters.equals(company.headquarters) : company.headquarters != null)
            return false;
        if (!holdings.equals(company.holdings)) return false;
        if (subsidiaries != null ? !subsidiaries.equals(company.subsidiaries) : company.subsidiaries != null)
            return false;
        if (valuation != null ? !valuation.equals(company.valuation) : company.valuation != null) return false;

        return true;
    }public String getName(){
        return this.name;
    }

    public boolean isPrivate(){
        return this.isPrivate;
    }

    public void goPublic(){
        this.isPrivate = true;
    }

    public void setName(String name){
        this.name = name;
    }


    @Override
    public int hashCode() {
        int result;
        long temp;
        result = holdings.hashCode();
        result = 31 * result + (corporateStock != null ? corporateStock.hashCode() : 0);
        result = 31 * result + (shares.size() ^ (shares.size() >>> 16));
        result = 31 * result + (isSubsidiary ? 1 : 0);
        result = 31 * result + (ceo != null ? ceo.hashCode() : 0);
        result = 31 * result + (headquarters != null ? headquarters.hashCode() : 0);
        result = 31 * result + (valuation != null ? valuation.hashCode() : 0);
        result = 31 * result + (country != null ? country.hashCode() : 0);
        result = 31 * result + (earnings != null ? earnings.hashCode() : 0);
        result = 31 * result + (costs != null ? costs.hashCode() : 0);
        result = 31 * result + (cash != null ? cash.hashCode() : 0);
        temp = Double.doubleToLongBits(earningsPerShare);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(dividend);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (subsidiaries != null ? subsidiaries.hashCode() : 0);
        return result;
    }

    @NotNull
    public ArrayList<Stock> getHoldings() {
        return holdings;
    }

    public void setHoldings(@NotNull ArrayList<Stock> holdings) {
        this.holdings = holdings;
    }

    public Stock getCorporateStock() {
        return corporateStock;
    }

    public void setCorporateStock(Stock corporateStock) {
        this.corporateStock = corporateStock;
    }

    public long getNumShares() {
        long s = 0L;
        for(Stock share : shares){
            s += share.getShares();
        }
        return s;
    }

    public boolean isSubsidiary() {
        return isSubsidiary;
    }

    public void setSubsidiary(boolean isSubsidiary) {
        this.isSubsidiary = isSubsidiary;
    }

    public String getCeo() {
        return ceo;
    }

    public void setCeo(String ceo) {
        this.ceo = ceo;
    }

    public String getHeadquarters() {
        return headquarters;
    }

    public void setHeadquarters(String headquarters) {
        this.headquarters = headquarters;
    }

    public Money getValuation() {
        return valuation;
    }

    public void setValuation(Money valuation) {
        this.valuation = valuation;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public Money getEarnings() {
        return earnings;
    }

    public void setEarnings(Money earnings) {
        this.earnings = earnings;
    }

    public Money getCosts() {
        return costs;
    }

    public void setCosts(Money costs) {
        this.costs = costs;
    }

    public Money getCash() {
        return cash;
    }

    public void setCash(Money cash) {
        this.cash = cash;
    }

    public double getEarningsPerShare() {
        return earningsPerShare;
    }

    public void setEarningsPerShare(double earningsPerShare) {
        this.earningsPerShare = earningsPerShare;
    }

    public double getDividend() {
        return dividend;
    }

    public void setDividend(double dividend) {
        this.dividend = dividend;
    }

    public ArrayList<Airline> getSubsidiaries() {
        return subsidiaries;
    }

    public void setSubsidiaries(ArrayList<Airline> subsidiaries) {
        this.subsidiaries = subsidiaries;
    }

    public void pay(Money money){
        cash.minus(money);
        costs.plus(money);
    }

    public void receive(Money money){
        cash.plus(money);
        earnings.plus(money);
    }

    public void receive(double amount){
        cash.plus(amount);
        earnings.plus(amount);
    }

    public void pay(double amount){
        cash.minus(amount);
        costs.plus(amount);
    }

    @Deprecated
    public double getFunds(){
        return this.getCash().getAmount().doubleValue();
    }
    public ArrayList<Contract> getContracts() {
        return contracts;
    }

    public void setContracts(ArrayList<Contract> contracts) {
        this.contracts = contracts;
    }

    public synchronized static Money value(Company c){
        return c.valueOfAssets().plus(c.getCash());
    }

    public synchronized Money valueOfAssets(){
        Money money = Money.of(CurrencyUnit.USD, 0.0d);
        holdings.forEach(h -> money.plus(h.getCurrentPrice().multipliedBy(h.getShares())));
        return money;
    }

    public ArrayList<Gate> getGates(){
        return gatesOwned;
    }

    public void setGates(ArrayList<Gate> gates){
        this.gatesOwned = gates;
    }
}
