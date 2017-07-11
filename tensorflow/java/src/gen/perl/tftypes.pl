use strict;
my $count;

my $option = '-t', my $template;

sub usage {
    print "Usage: tftypes [-ctdT] <type desc file> <tmpl file>\n\n"
         ."This script generates parts of various .java files that depend on which"
         ."TensorFlow types are supported by the Java API and how much. For each"
         ."such .java file, there is a .tmpl file in the same source directory in"
         ."which the strings \@TYPEINFO\@ and \@IMPORTS\@ are replaced with"
         ."appropriate Java code. Output code is sent to standard output.\n";
}

if ($ARGV[0] =~ m/^-/) {
    $option = shift;
}
my $typedesc = shift;
my $tmpl = shift;

open (TMPL, "<$tmpl") || die "Cannot open $tmpl for reading\n";

my $text = do { local $/; <TMPL> };

my %jtypecount;

my $typeinfo, my $imports;

open (TYPEDESC, $typedesc);

my @info = ([]);

while (<TYPEDESC>) {
    chomp;
    my $line = $_;
    if ($line =~ m/^TF type/) { next }
    $line =~ s/\r$//;
    (my $name, my $index, my $jtype, my $jbox, my $creat, my $default, my $desc) =
        split /,/, $line, 7;
    $desc =~ s/^ *//g;
    $desc =~ s/ *$//g;
    $jtypecount{$jtype}++;
    if ($jtypecount{$jtype} > 1) {
# currently allowing Java types to stand for more than one TF type, but
# may want to revisit this.
#       print STDERR "Ambiguous Java type for $name : $jtype\n";
#       exit 1
    }

    push @info, [$name, $index, $jtype, $jbox, $creat, $default, $desc];
}

print "// GENERATED FILE. Edits to this file will be lost -- edit $tmpl instead.\n";

my $first = 1;

for (my $i = 1; $i <= $#info; $first = 0, $i++) {
    (my $name, my $index, my $jtype, my $jbox, my $creat, my $default, my $desc) =
        @{$info[$i]};
    my $tfname = "TF".$name;
    my $ucname = uc $name;

    if ($option eq '-t') {
        # Generate type declarations for Types.java
        if (defined($desc) && $desc ne '') {
            $typeinfo .=
"  // $desc\n";
        }
        $typeinfo .= "  public static class $tfname implements TFType {}\n"
                    ."  public static final Class<$tfname> $ucname = $tfname.class;\n"
                    ."  static { typeCodes.put($ucname, $index); }";
        if ($default ne '') {
            $typeinfo .= "  static { scalars.put($ucname, $default); }\n";
        }
        $typeinfo .= "\n";
    } elsif ($option eq '-d') {
      # Generate datatype enums for DataType.java
      # TODO: implement
      if ($jtype ne '') {
        if (!$first) {
            $typeinfo .= ",\n\n";
        }
        if ($desc ne '') {
            $typeinfo .= "  /** $desc. */\n";
        }
        $typeinfo .=   "  $ucname($index)";
      }
    } elsif ($option eq '-c') { # creators
      # Generate creator declarations for Tensors.java
      if ($jtype ne '' && $creat eq 'y') {
        for (my $brackets = ''; length $brackets <= 12; $brackets .= '[]') {
            $typeinfo .=
                "  public static Tensor<$tfname> create($jtype$brackets data) {\n"
               ."    return Tensor.create(data, $ucname);\n"
               ."  }\n";
        }
      }
      if ($text =~ m/\b$tfname\b/ || $creat eq 'y') {
            $imports .= "import org.tensorflow.Types.$tfname;\n";
      }
      if ($text =~ m/\b$ucname\b/ || $creat eq 'y') {
            $imports .= "import static org.tensorflow.Types.$ucname;\n";
      }
    } elsif ($option eq '-T') { # Tensor.java
      if ($text =~ m/\b$tfname\b/) {
            $imports .= "import org.tensorflow.Types.$tfname;\n";
      }
#     if ($text =~ m/\b$ucname\b/) {
#           $imports .= "import static org.tensorflow.Types.$ucname;\n";
#     }
    }
}

$text =~ s/\@TYPEINFO\@/$typeinfo/;
$text =~ s/\@IMPORTS\@/$imports/;

print $text;
