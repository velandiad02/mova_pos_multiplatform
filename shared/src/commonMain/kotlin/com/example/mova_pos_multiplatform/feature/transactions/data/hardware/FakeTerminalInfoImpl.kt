package com.example.mova_pos_multiplatform.feature.transactions.data.hardware

import com.example.mova_pos_multiplatform.feature.transactions.domain.boundary.hardware.TerminalInfo

class FakeTerminalInfoImpl : TerminalInfo {

    override fun getTerminalSerialNumber(): String = "SIM-POS-A1B2C3D4"
}