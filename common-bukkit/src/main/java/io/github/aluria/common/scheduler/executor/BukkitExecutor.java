package io.github.aluria.common.scheduler.executor;

import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public interface BukkitExecutor extends Executor {

  void runTaskTimer(Runnable runnable, long delay, long period);

  /**
   * Executa uma nova task repetida, porém com delay
   * equivalente a 0.
   *
   * @param runnable ação que vai ser executada repetidamente.
   * @param time     tempo de repetição
   * @param unit     unidade do tempo de repetição.
   */
  default void runInstantTaskTimer(Runnable runnable, long time, TimeUnit unit) {
    this.runTaskTimer(runnable, 0, 20 * unit.toSeconds(time));
  }

  void runTaskLater(Runnable runnable, long delay);

  /**
   * Executa uma nova task depois de um tempo
   *
   * @param runnable ação que vai ser executada no futuro.
   * @param time     tempo para a ação ser executada.
   * @param unit     unidade do tempo da execução.
   */

  default void runInstantTaskLater(Runnable runnable, long time, TimeUnit unit) {
    this.runTaskLater(runnable, 20 * unit.toSeconds(time));
  }

  void runTask(Runnable runnable);


  @Override
  default void execute(Runnable runnable) {
    this.runTask(runnable);
  }
}
