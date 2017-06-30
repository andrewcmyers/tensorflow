use strict;
my $count;

my $option = '-t', my $template;

sub usage {
    print "Usage: tftypes [-ctd] <type desc file>\n";
}

if ($ARGV[0] =~ m/^-/) {
    $option = shift;
}
my $typedesc = shift;

my $text = do { local $/; <STDIN> };

my %jtypecount;

my $output;

open (TYPEDESC, $typedesc);

my @info = ([]);

while (<TYPEDESC>) {
    chomp;
    my $line = $_;
    if ($line =~ m/^TF type/) { next }
    $line =~ s/\r$//;
    (my $name, my $jtype, my $jbox, my $creat, my $desc) = split /,/, $line, 5;
    $desc =~ s/^ *//g;
    $desc =~ s/ *$//g;

    push @info, [$name, $jtype, $jbox, $creat, $desc];
    $jtypecount{$jtype}++;
}

for (my $i = 1; $i <= $#info; $i++) {

    (my $name, my $jtype, my $jbox, my $creat, my $desc) = @{$info[$i]};
    my $tfname = "TF".$name;
    my $ucname = uc $name;

    if ($option eq '-t') {
        if (defined($desc) && $desc ne '') {
            $output .=
"  // $desc\n";
        }
        $output .=
"  public static class $tfname implements TFType {}
  public static final Class<$tfname> $ucname = $tfname.class;
  { typeCodes.put($ucname, $i); }

";
    } elsif ($option eq '-d') {
    } elsif ($option eq '-c') { # creators
      if ($jtype ne '' && $creat eq 'y') {
        for (my $brackets = ''; length $brackets <= 12; $brackets .= '[]') {
            $output .=
"  public static Tensor<$tfname> create($jtype$brackets data) {
    return Tensor.create(data, Types.$ucname);
  }
";
        }
      }
    }
}

$text =~ s/\@TYPEINFO\@/$output/;

print $text;
