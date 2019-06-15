package pt.ulht.cm.projeto.servicodeurgencias.model;

import java.util.ArrayList;
import java.util.List;

public abstract  class HospitalProviderAbstract implements IHospitalProvider {

    protected HospitalProviderAbstract() {
        observers = new ArrayList<>();
    }

    protected List<Hospital> hospitalData;
    private List<HospitalProviderObserver> observers;

    @Override
    public void addObserver(HospitalProviderObserver observer) {
        if(!observers.contains(observer)) {
            observers.add(observer);
        }
    }

    @Override
    public void removeObserver(HospitalProviderObserver observer) {
        if(observers.contains(observer)) {
            observers.remove(observer);
        }
    }

    @Override
    public void notifyObserverDataChanged() {
        for(HospitalProviderObserver hpo : observers) {
            hpo.updateData(hospitalData);
        }
    }
}
