public class Person {
    
    private String cardNumber;
    private String pin;

    public Person(String cardNumber, String pin) {
        this.cardNumber = cardNumber;
        this.pin = pin;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return cardNumber.equals(person.cardNumber);
    }

    @Override
    public int hashCode() {
        return cardNumber.hashCode();
    }

    @Override
    public String toString() {
        return "Person{" +
                "cardNumber='" + cardNumber + '\'' +
                ", pin='" + pin + '\'' +
                '}';
    }
}
