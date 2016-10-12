#! /usr/bin/perl

use strict;
my $class = shift;
my $suf = ".csv";
open(IN, "<$class$suf") || die "Cannot read: $class$suf\n";
my $sep = ":";
my $tab = 8;
my $q = '"';

# read header lines: titles, max grades
my @hdr = &readSplit();
my @max = &readSplit();
push(@max, '100%');

# read students
my @student;
while (<IN>) {
    chomp;
    tr /"//d;  # "
    push(@student, $_);
}
my @ave = split(/$sep/, pop(@student));

# gen mail for each student
my $ct = 0;
foreach (@student) {
    my @p = split(/$sep/);
    $ct += &sendMail(@p);
}
$ave[1] = $ENV{"USER"};
&sendMail(@ave);
print "Emails sent: $ct\n";
exit;
