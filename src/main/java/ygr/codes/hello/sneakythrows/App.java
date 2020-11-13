package ygr.codes.hello.sneakythrows;

import lombok.SneakyThrows;

import static java.lang.System.out;

public class App {
  public static void main(String[] args) throws InterruptedException {
    goodSleep();
    out.println("===");
    badSleep();
  }

  private static void goodSleep() throws InterruptedException {
    Thread t = new Thread(new WarmBed(new NiceSleeper()));
    t.start();
    out.println("[Monster] Sleeper sleeping and we wait...");
    Thread.sleep(5000);
    out.println("[Monster] Let's wake up the sleeper...");
    t.interrupt();
    t.join();
  }

  private static void badSleep() throws InterruptedException {
    Thread t = new Thread(new WarmBed(new BadSleeper()));
    t.start();
    out.println("[Monster] Sleeper sleeping and we wait...");
    Thread.sleep(5000);
    out.println("[Monster] Let's wake up the sleeper...");
    t.interrupt();
  }
}

class WarmBed implements Runnable {

  private final Sleeper sleeper;

  WarmBed(final Sleeper sleeper) {
    this.sleeper = sleeper;
  }

  @Override
  public void run() {
    while (!Thread.currentThread().isInterrupted()) {
      sleeper.sleep();
    }
    out.println("[Bed] My dear dwarfs please clean me - your bed...");
  }
}

interface Sleeper {
  void sleep();
}

class NiceSleeper implements Sleeper {
  @Override
  public void sleep() {
    out.println("[NiceSleeper] Sleeping...");
    try {
      Thread.sleep(1000L);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      out.println("[NiceSleeper] Someone woke me up, let's inform my bed than...");
    }
  }
}

class BadSleeper implements Sleeper {

  @SneakyThrows
  @Override
  public void sleep() {
    out.println("[BadSleeper] Sleeping...");
    Thread.sleep(1000L);
  }
}
