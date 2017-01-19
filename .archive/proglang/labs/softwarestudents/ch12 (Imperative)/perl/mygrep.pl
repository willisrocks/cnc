#! /usr/bin/perl

die "Usage mygrep string \n" if @ARGV < 1;
use strict;
my $string = shift;
my $ct = 0;

while (<>) {
    $ct++;
    print "$ct:\t$_" if /$string/;
}
exit;
