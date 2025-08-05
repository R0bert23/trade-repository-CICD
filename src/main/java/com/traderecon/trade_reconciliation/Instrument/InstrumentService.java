package com.traderecon.trade_reconciliation.Instrument;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InstrumentService {

    @Autowired
    private InstrumentRepository instrumentRepository;

    public List<Instrument> getAll() {
        return instrumentRepository.findAll();
    }

    public Optional<Instrument> getById(Long id) {
        return instrumentRepository.findById(id);
    }

    public Instrument save(Instrument instrument) {
        return instrumentRepository.save(instrument);
    }

    public Optional<Instrument> update(Long id, Instrument newInstrument) {
        return instrumentRepository.findById(id).map(existing -> {
            existing.setName(newInstrument.getName());
            existing.setSymbol(newInstrument.getSymbol());
            existing.setIsin(newInstrument.getIsin());
            return instrumentRepository.save(existing);
        });
    }

    public boolean delete(Long id) {
        if (instrumentRepository.existsById(id)) {
            instrumentRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}
