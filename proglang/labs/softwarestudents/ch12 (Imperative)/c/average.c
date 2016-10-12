#include <stdio.h>

int main(int argc, char *argv[ ]) {
    int ct, number, min, max, sum;
    sum = ct = 0;

    printf("Enter number: ");
    while (scanf("%d", &number) != EOF) {
        if (ct == 0)
            min = max = number;
        ct++;
        sum += number;
        min = number < min ? number : min;
        max = number > max ? number : max;
        printf("Enter number: ");
    }
    printf("%d numbers read\n", ct);
    if (ct > 0) {
        printf("Average:\t%d\n", sum / ct);
        printf("Maximum:\t%d\n", max);
        printf("Minimum:\t%d\n", min);
    }
}
