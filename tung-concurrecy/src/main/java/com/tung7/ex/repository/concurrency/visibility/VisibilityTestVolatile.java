package com.tung7.ex.repository.concurrency.visibility;

/**
 * 体验可见性
 * <p>1. 使用volatile</p>
 *
 * <p>2. 使用synchronized，锁住某变量，这样在synchronized释放monitor的时候，就会把此变量同步回主内存中(执行store和write操作)</p>
 *
 * @author Tung
 * @version 1.0
 * @date 2017/5/29.
 * @update
 */

public class VisibilityTestVolatile extends Thread {

    /**
     *  如果没有加volatile,那么主线程对flag的修改，子线程是不知道的，
     *
     *  <p>（因为子线程已经将flag缓存到了寄存器中, 并且JVM将指令优化成了：循环不再读取flag的值，也就是说，汇编指令上已经是一个死循环了。）</p>
     *
     *  <pre>
     *  xxxxxxx: movvzx eax, byte ptr[ecx+64h] ;获取flag的值
     *  xxxxxxx: test eax, eax ;判断flag真假
     *  xxxxxxx: jne 193bfafh ;根据上一步结果跳转。
     *  193bfac: inc ebx  ;i自增
     *  193bfad: jmp 193bfach ;无条件跳转到193bfac指令地址上，也就是继续自增， 没有去取flag值进行判断。
     *  </pre>
     *
     *  <p> 在加上了volatile之后，JVM为了保证语意， 就会在每次自增后重新获取flag的值进行判断。 </p>
     *
     *  <p> 需要注意的是，加不加volatile关键字，class文件中关于run方法的字节码描述其实一样的。 不知道为什么 </p>
     *
     *  <pre>
     *      stack=3, locals=1, args_size=1
     *           0: aload_0
     *           1: getfield      #2                  // Field flag:Z ;获取flag
     *           4: ifne          20                  // 判断，如果false,跳转到20行
     *           7: aload_0
     *           8: dup
     *           9: getfield      #3                  // Field i:I ;获取i
     *           12: iconst_1
     *           13: iadd                              // Field i:I ;自增i
     *           14: putfield      #3                  // Field i:I ;写回i
     *           17: goto          0                   // 无条件跳转到 0; 在这里，它似乎总是获取值，比较值。
     *           20: getstatic     #4                  // Field java/lang/System.out:Ljava/io/PrintStream; 输出结果
     *  </pre>
     *
     */
    private volatile boolean flag = false;
//    private boolean flag = false;

    private int i = 0;
    @Override
    public void run() {
        while (!flag) {
            i++;
        }
        System.out.println("Finished: " + i);
    }

    public  void stopInc() {
        flag = true;
    }

    public boolean isFlag() {
        return flag;
    }

    public static void main(String[] args) throws InterruptedException {
        VisibilityTestVolatile t = new VisibilityTestVolatile();
        t.start();

        // 主线程等到500ms, 让i自增执行一段时间。
        Thread.sleep(500);

        t.stopInc(); // 倘若flag没有volatile修饰，则不会停止。
    }

}
