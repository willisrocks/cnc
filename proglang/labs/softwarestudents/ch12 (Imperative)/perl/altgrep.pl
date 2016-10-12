#! /usr/bin/perl

if (@ARGV < 1) {
    die "Usage mygrep string \n" ;
}
use strict;
my $string = shift(@ARGV);
my $ct = 0;
my $line;

while ($line = <>) {
    $ct++;
    if ($line =~ m/$string/) {
        print STDOUT $ct, ":\t", $line;
    }
}
exit;
