package com.jileklu2.bakalarska_prace_app.mapObjects;

public class Variable {

    private final String name;
    private final String value;

    public Variable(String name, String value) {
        this.name = String.valueOf(name);
        this.value =  String.valueOf(value);
    }

    @Override
    public String toString() {
        return String.format("name:%s:value:%s", name, value);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Variable other = (Variable) obj;

        // https://stackoverflow.com/questions/25366441/check-which-combinations-of-parameters-are-null
        int switchvar = 0;
        if (name == null) {switchvar += 1;}
        if (value == null) {switchvar += 10;}

        switch (switchvar) {
            case(1):
                return other.name == null;
            case(10):
                return other.value == null;
            case(11):
                return other.name == null && other.value == null;
        }

        return this.name.equals(other.name) &&
                this.value.equals(other.value);
    }

    @Override
    public int hashCode() {
        final int prime = 5;
        int result = 1;
        int add = 0;

        int switchvar = 0;
        if (name == null) {switchvar += 1;}
        if (value == null) {switchvar += 10;}

        switch (switchvar) {
            case(0):
                add = name.hashCode();
                break;
            case(1):
                add = value.hashCode();
                break;
            case(11):
                add = 0;
                break;
        }

        result = prime * result + add;
        return result;
    }
}
