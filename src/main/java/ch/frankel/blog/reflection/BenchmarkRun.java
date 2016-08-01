package ch.frankel.blog.reflection;

import org.openjdk.jmh.annotations.Benchmark;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.util.Date;

public class BenchmarkRun {

    @Benchmark
    public void runImmutableWithoutReflection() {
        getWithoutReflection(newImmutablePerson());
    }

    @Benchmark
    public void runMutableWithoutReflection() {
        getWithoutReflection(newMutablePerson());
    }

    @Benchmark
    public void runImmutableWithReflection() throws Exception {
        getWithReflection(newImmutablePerson());
    }

    @Benchmark
    public void runMutableWithReflection() throws Exception {
        getWithReflection(newMutablePerson());
    }

    private Person newImmutablePerson() {
        return new ImmutablePerson("John", "Doe", new Date());
    }

    private Person newMutablePerson() {
        MutablePerson person = new MutablePerson();
        person.setFirstName("John");
        person.setLastName("Doe");
        person.setBirthDate(new Date());
        return person;
    }

    private void getWithoutReflection(Person person) {
        person.getFirstName();
        person.getLastName();
        person.getBirthDate();
    }

    private void getWithReflection(Person person) throws Exception {
        Class<? extends Person> clazz;
        if (person instanceof MutablePerson) {
            clazz = MutablePerson.class;
        } else {
            clazz = ImmutablePerson.class;
        }
        Field firstName = clazz.getDeclaredField("firstName");
        Field lastName = clazz.getDeclaredField("lastName");
        Field birthDate = clazz.getDeclaredField("birthDate");
        Field.setAccessible(new AccessibleObject[] { firstName, lastName, birthDate }, true);
        firstName.get(person);
        lastName.get(person);
        birthDate.get(person);
    }
}
