package com.project.donate.core.model.dtos;

import java.util.List;

public class ExecutorsDTO {
    private List<ExecutorDTO> chosenExecutors;

    private class ExecutorDTO {
        private String name;
        private String address;
        private double amount;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public double getAmount() {
            return amount;
        }

        public void setAmount(double amount) {
            this.amount = amount;
        }
    }
}
