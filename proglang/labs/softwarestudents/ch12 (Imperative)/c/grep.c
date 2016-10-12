#include <stdio.h>
#define SIZE 1024

int main(int argc, char *argv[ ]) {
    char line[SIZE];
    int count;
    FILE *in;

    if (argc < 3) {
        fprintf(stderr, "Usage: %s string file\n", *argv);
        exit(1);
    }

    
