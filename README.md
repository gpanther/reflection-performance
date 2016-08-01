# Performance cost of reflection

This project evaluates the performance overhead of using Java's reflection API to access attributes.

To run, first build the package with Maven:

```bash
mvn package
```

Then run the tests:

```bash
java -jar target/benchmarks.jar
```

Be prepared to wait for some time.

Results should look like the following:

```
Benchmark                                    Mode  Cnt         Score        Error  Units
BenchmarkRun.runImmutableWithReflection     thrpt  200   2492673.501 ±  37994.941  ops/s
BenchmarkRun.runImmutableWithoutReflection  thrpt  200  26499946.587 ± 242499.198  ops/s
BenchmarkRun.runMutableWithReflection       thrpt  200   2505239.277 ±  27697.028  ops/s
BenchmarkRun.runMutableWithoutReflection    thrpt  200  26635097.050 ± 150798.911  ops/s
```

The associated blog article can be found [here](https://blog.frankel.ch/performance-cost-of-reflection/).