package fr.yncrea.m1_s1project_android.models;

//vf : Intensité (tension), Voltage (courant)
public enum PowerSupply {
    I(0), V(1);

    private final int mValue;

    PowerSupply(final int value) {
        mValue = value;
    }

    public int getValue() {
        return this.mValue;
    }
}