package com.home.service;

import com.home.model.Saving;
import com.home.repository.SavingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SavingDAO {
    @Autowired
    private SavingRepository savingRepository;

    public SavingDAO() {
    }

    public void saveSaving(Saving saving) {
        savingRepository.save(saving);
    }

    public List<Saving> findAllSavingsByAccountId(String accountId) {
        return savingRepository.findByAccountId(accountId);
    }

    public Saving findSavingById(Integer id) {
        return savingRepository.findById(id).orElse(null);
    }

    public Boolean accrueMoneyById(int id, double money) {
        Saving saving = findSavingById(id);
        if(saving.accrueMoney(money)) {
            saveSaving(saving);
            return true;
        }

        return false;
    }

    public Boolean takeMoneyById(int id, double money) {
        Saving saving = findSavingById(id);
        if(saving.takeMoney(money)) {
            saveSaving(saving);
            return true;
        }

        return false;
    }
}
