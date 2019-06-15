package pt.ulht.cm.projeto.servicodeurgencias.model;

import java.util.List;

public interface IHospitalProvider {
    List<Hospital> getHospitals();
    Hospital getHospital(int hospitalID);

    // Observable methods
    void addObserver(HospitalProviderObserver observer);
    void removeObserver(HospitalProviderObserver observer);
    void notifyObserverDataChanged();

    interface HospitalProviderObserver {
        void updateData(List<Hospital> newDataSet);
    }
}
