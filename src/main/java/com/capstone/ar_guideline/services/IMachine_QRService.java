package com.capstone.ar_guideline.services;

import com.capstone.ar_guideline.entities.Machine_QR;
import java.util.List;

public interface IMachine_QRService {
  Machine_QR create(Machine_QR machineQr);

  Machine_QR update(String id, Machine_QR machineQr);

  void delete(String id);

  Machine_QR findById(String id);

  List<Machine_QR> getByMachineId(String machineId);
}
