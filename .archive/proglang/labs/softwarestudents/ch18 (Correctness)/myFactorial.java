/*@ requires 1 <= n;
    ensures \result == (\product int i; 1<=i && i<=n; i);
  @*/
static int Factorial (int n) {
  int f = 1;
  int i = 1;
/*@ loop_invariant i <= n && 
         f == (\product int j; 1 <= j && j <= i; j);
  @*/
  while (i < n) {
    i = i + 1;
    f = f * i;
  }
  return f;
}
public static void main(String[] args) {
   int n = Integer.parseInt(args[0]);
   System.out.println("Factorial of " + n + 
                      " = " + Factorial(n));
}
}
