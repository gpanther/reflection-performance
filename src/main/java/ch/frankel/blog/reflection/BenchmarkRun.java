package ch.frankel.blog.reflection;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;

import com.esotericsoftware.reflectasm.MethodAccess;

import java.lang.reflect.Method;
import java.util.Date;

@State(Scope.Thread)
public class BenchmarkRun {
	public Person immutablePerson = newImmutablePerson();
	public Person mutablePerson = newMutablePerson();

    @Benchmark
    public String runImmutableWithoutReflection() {
        return getWithoutReflection(immutablePerson);
    }

    @Benchmark
    public String runMutableWithoutReflection() {
        return getWithoutReflection(mutablePerson);
    }

    @Benchmark
    public String runImmutableWithReflection() throws Exception {
        return getWithReflection(immutablePerson);
    }

    @Benchmark
    public String runMutableWithReflection() throws Exception {
        return getWithReflection(mutablePerson);
    }

    @Benchmark
    public String runImmutableWithReflectAsm() throws Exception {
        return getWithReflectAsm(immutablePerson);
    }

    @Benchmark
    public String runMutableWithReflectAsm() throws Exception {
        return getWithReflectAsm(mutablePerson);
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

    private String getWithoutReflection(Person person) {
        return person.getFirstName();
    }

    private static final Method PERSON_GETTER = createGetter();

    private String getWithReflection(Person person) throws Exception {
    	return (String)PERSON_GETTER.invoke(person);
    }

	private static Method createGetter() {
		try {
			return Person.class.getMethod("getFirstName");
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	private static final MethodAccess METHOD_ACCESS;
	private static final int METHOD_IDX;

	static {
		METHOD_ACCESS = MethodAccess.get(Person.class);
		METHOD_IDX = METHOD_ACCESS.getIndex("getFirstName");
	}

	private String getWithReflectAsm(Person person) throws Exception {
		return (String) METHOD_ACCESS.invoke(person, METHOD_IDX);
	}
}
