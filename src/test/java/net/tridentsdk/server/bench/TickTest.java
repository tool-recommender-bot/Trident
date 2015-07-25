/*
 * Trident - A Multithreaded Server Alternative
 * Copyright 2014 The TridentSDK Team
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.tridentsdk.server.bench;

import net.tridentsdk.registry.Factory;
import net.tridentsdk.registry.Registered;
import net.tridentsdk.server.concurrent.MainThread;
import net.tridentsdk.server.service.TridentImpl;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;

/*
http://bit.ly/1AZxBL6

tick 5.011719043333334E7
tick 5.0066712083333336E7
tick 5.006329321666667E7
tick 5.010468563333334E7
tick 5.016053133333333E7
tick 5.012393275E7
tick 5.007866225E7
tick 5.01629668E7
tick 5.010474393333333E7
tick 5.01045108E7
tick 5.0183914333333336E7
 */
// Used for baseline measurements
@State(Scope.Benchmark)
public class TickTest {
    static {
        TridentImpl trident = new TridentImpl();
        Factory.setProvider(trident);
        Registered.setProvider(trident);
    }

    private static final MainThread THREAD = new MainThread(20);
    //@Param({ "1", "2", "4", "8", "16", "32", "64", "128", "256", "512", "1024" })
    private int cpuTokens;

    public static void main0(String[] args) {
        for (int i = 0; i < 100; i++) {
            try {
                THREAD.doRun();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder().include(".*" + TickTest.class.getSimpleName() + ".*") // CLASS
                .timeUnit(TimeUnit.MILLISECONDS)
                .mode(Mode.AverageTime)
                .warmupIterations(20)
                .measurementIterations(5)         // ALLOWED TIME
                .forks(1)                                           // FORKS
                //.verbosity(VerboseMode.SILENT)                      // GRAPH
                .threads(1)                                         // THREADS
                .build();

        Benchmarks.chart(Benchmarks.parse(new Runner(opt).run()), "Tick+Length"); // TITLE
    }

    //@Benchmark
    public void control() {
        Blackhole.consumeCPU(cpuTokens);
    }

    @Benchmark
    public void tick() {
        try {
            THREAD.doRun();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
