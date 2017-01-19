#! /usr/bin/perl

use Text::Tabs;
use Getopt::Std;

my $ct = 1;
getopts("a");
$ct = 9999 if $opt_a;

if (@ARGV < 1) {
    print "Usage: remove.pl [-a]  fromStr \n";
    exit;
}

use strict;
my $debug = 0;
my $from = shift;
print STDERR "From: $from\n" if $debug;
my $rm = 0;
my $done = 0;
my $trim = 0;
my $linect = 0;
my $braces = 0;
my $lb = '{';
my $rb = '}';

while (<>) {
    chomp;
    expand;
    if ( $rm ) {
        my @lbc = split/$lb/, $_, -1;
        my @rbc = split/$rb/, $_, -1;
        my $oldbr = $braces;
        $braces += @lbc - @rbc;
        if ($braces == 0) {
            $rm = 0;
            while (<>) {
                last unless /^\s*\n$/;
            }
            chomp;
            expand;
        }
    }
    if ($rm == 0 && index($_, $from)+1 && $ct) {
        $rm = 1;
        $ct--;
        my @lbc = split/$lb/, $_, -1;
        my @rbc = split/$rb/, $_, -1;
        my $oldbr = $braces;
        $braces += @lbc - @rbc;
    }
    next if $rm == 1;
    print "$_\n";
    $linect++;
}

print STDERR "remove.pl: $linect lines written\n";
exit;


sub wslen {
    my $a = shift;
    my $t = $a;
    $t =~ s/^\s*//;
    my $wsl = length($a) - length($t);
    print STDERR "WS Length: $wsl\n" if $debug;
    return $wsl;
}

=head1 DESCRIPTION

I<remove.pl> is intended to remove code from 1 or more methods
of a Java class.  Input is from I<stdin> and output is to I<stdout>;
this facilitates using I<remove.pl> in a Unix pipe.

I<BeginString> is a string that, when found, stops copying to I<stdout>.
The line on which I<BeginString> occurs is B<not> copied.  Substring
matching is used, B<not> regular expressions; seems to be less
problematic.  I<BeginString> should be enough of the line
header to uniquely identify the line.  For example:

        remove.pl  "void display"  ...

B<Note> that the spacing must match exactly.

Program works by counting left and right braces.  Deleting stops when
the brace count == 0.  Also deletes following blank lines.

=head1 OPTIONS

=over 5

=item B<-a>

Deletes all occurrences of the method.  For example, in the abstract
syntax Java file, it is used to delete all the display methods.

=back

=head1 BUGS

=over 5

=item 1.

Assumes that the first left brace occurs on the first line deleted.
Otherwise it will stop deleting immediately.

=item 2.

Not smart enough to not count braces which occur in strings and
    comments. All occurrences of braces must be balanced.

=back

=head1 AUTHOR

R. Noonan <noonan@cs.wm.edu>

=cut
