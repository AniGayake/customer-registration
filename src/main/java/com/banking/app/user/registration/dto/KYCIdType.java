package com.banking.app.user.registration.dto;

import java.util.HashMap;
import java.util.Map;

public enum KYCIdType {
    AADHAR("1","AADHAR"),
    PAN("2","PAN"),
    VOTERID("3","VOTERID"),
    PASSPORT("4","PASSPORT");

    private String kycIdTypeCode;
    private String name;
    KYCIdType(String kycIdTypeCode,String name) {
        this.kycIdTypeCode = kycIdTypeCode;
        this.name = name;
    }
    public String getKycIdTypeCode(){
        return kycIdTypeCode;
    }

    public String getName() {
        return name;
    }

    private static final Map<String,String> LOOKUP = new HashMap<>();

    static {
        for(KYCIdType kycIdType:KYCIdType.values()){
            LOOKUP.put(kycIdType.getName(), kycIdType.getKycIdTypeCode());
        }
    }
    public static String getKycIdType(String code){
        return LOOKUP.get(code);
    }
}
