sub readSplit {
    $_ = <IN>;
    chomp;
    tr /$q//d;
    my @r = split(/$sep/);
    return @r;
}

sub sendMail {
    my $name = shift;
    my $email = shift;
    return 0 unless $email;
    open(MAIL, "| mail -s '$class Grades' $email")
        || die "Cannot fork mail: $!\n";
    print MAIL "GRADE\t\tYOUR\tMAX\tCLASS\n",
                "NAME\t\tSCORE\tSCORE\tAVE\n\n";

    my $ct = 1;
    foreach (@_) {
        $ct++;
        next unless $hdr[$ct];
        print MAIL "$hdr[$ct]\t";
        print MAIL "\t" if length($hdr[$ct]) < $tab;
        if (/^\d/) { print MAIL int($_ + 0.5); }
        else { print MAIL $_; }
        print MAIL "\t$max[$ct]\t";
        if ($ave[$ct] =~ /^\d/) { 
            print MAIL int($ave[$ct] + 0.5); 
        } else { print MAIL $ave[$ct];}
        print MAIL "\n";
    }
    return 1;
}
