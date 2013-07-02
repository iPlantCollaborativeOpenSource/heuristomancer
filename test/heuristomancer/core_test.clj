(ns heuristomancer.core-test
  (:use [clojure.test]
        [heuristomancer.core]
        [heuristomancer.loader :only [resource-reader]]))

(defn- parse-test-file
  "Parses a test file and returns the result."
  [path]
  (with-open [r (resource-reader path)]
    (identify r)))

(defn- test-file-type-identification
  "Verifies that a file type is identified correctly."
  [[path expected]]
  (testing (str path " identified as " expected)
    (is (= expected (parse-test-file path)))))

(def ^:private tests
  [["foo.csh"                 :csh]
   ["foo.tcsh"                :tcsh]
   ["foo.sh"                  :sh]
   ["foo.bash"                :bash]
   ["foo.pl"                  :perl]
   ["foo.py"                  :python]
   ["dna-quote.ace"           :ace]
   ["dna-colon.ace"           :ace]
   ["peptide-quote.ace"       :ace]
   ["peptide-colon.ace"       :ace]
   ["protein-quote.ace"       :ace]
   ["protein-colon.ace"       :ace]
   ["sequence-quote.ace"      :ace]
   ["sequence-colon.ace"      :ace]
   ["test.ace"                :ace]
   ["blastp2215.blast"        :blast]
   ["bug2246.blast"           :blast]
   ["frac_problems.blast"     :blast]
   ["frac_problems2.blast"    :blast]
   ["frac_problems3.blast"    :blast]
   ["foo.bowtie"              :bowtie]
   ["test.bowtie"             :bowtie]
   ["foo.clustalw"            :clustalw]
   ["T7.aln"                  :clustalw]
   ["hs_owlmonkey.aln"        :clustalw]
   ["longnames.aln"           :clustalw]
   ["mini-align.aln"          :clustalw]
   ["pep-266.aln"             :clustalw]
   ["testaln.aln"             :clustalw]
   ["foo.codata"              :codata]
   ["foo.embl"                :embl]
   ["test.embl"               :embl]
   ["test.embl2sq"            :embl]
   ["alnfile.fasta"           :fasta]
   ["genomic-seq.fasta"       :fasta]
   ["hs_owlmonkey.fasta"      :fasta]
   ["test.fasta"              :fasta]
   ["testaln.fasta"           :fasta]
   ["testaln2.fasta"          :fasta]
   ["test.cns.fastq"          :fastq]
   ["test.fastq"              :fastq]
   ["test_clear_range.fastq"  :fastq]
   ["test_singlets.cns.fastq" :fastq]
   ["testaln.fastq"           :fastq]
   ["foo-xy.fastxy"           :fastxy]
   ["foo-a.fastxy"            :fastxy]
   ["test.game"               :game]
   ["test.gcg"                :gcg]
   ["aquilegia-traits.csv"    :csv]
   ["foo.csv"                 :csv]
   ["embedded_newlines.csv"   :csv]
   ["no_quotes.csv"           :csv]
   ["no_trailing_newline.csv" :csv]
   ["empty_last_record.csv"   :csv]
   ["partial_last_record.csv" :csv]
   ["partial_quote_field.csv" :csv]
   ["aquilegia-traits.tsv"    :tsv]
   ["foo.tsv"                 :tsv]
   ["embedded_newlines.tsv"   :tsv]
   ["no_quotes.tsv"           :tsv]
   ["no_trailing_newline.tsv" :tsv]
   ["empty_last_record.tsv"   :tsv]
   ["partial_last_record.tsv" :tsv]
   ["partial_quote_field.tsv" :tsv]
   ["test.gcgblast"           :gcgblast]
   ["test.gcgfasta"           :gcgfasta]
   ["test.genbank"            :genbank]
   ["dna.genbank"             :genbank]
   ["genomic-seq.genscan"     :genscan]
   ["no-genes.genscan"        :genscan]
   ["HUMBETGLOA.gff"          :gff]
   ["hg16_chroms.gff"         :gff]
   ["myco_sites.gff"          :gff]
   ["test.gff"                :gff]
   ["dna.phylip"              :phylip]
   ["dna.pir"                 :pir]
   ["protein.pir"             :pir]
   ["test.raw"                :raw]
   ["test2.raw"               :raw]
   ["test.hmmer"              :hmmer]
   ["L77119.hmmer"            :hmmer]
   ["test.mase"               :mase]
   ["test2.mase"              :mase]
   ["test3.mase"              :mase]
   ["test.mega"               :mega]
   ["test.msf"                :msf]
   ["cysprot.msf"             :msf]
   ["cysprot1a.msf"           :msf]
   ["cysprot1b.msf"           :msf]
   ["testaln.msf"             :msf]
   ["test.rsf"                :rsf]
   ["test.vcf"                :vcf]
   ["example.vcf"             :vcf]
   ["test.stockholm"          :stockholm]
   ["test.swiss"              :swiss]
   ["test.prodom"             :prodom]
   ["testaln.prodom"          :prodom]
   ["test.selex"              :selex]
   ["testaln.selex"           :selex]
   ["bug2335.fastq"           :fastq]
   ["error_diff_ids.fastq"    :fastq]
   ["error_double_qual.fastq" :fastq]
   ["sample1.newick"          :newick]
   ["abr-distance.newick"     :newick]
   ["all-distance.newick"     :newick]
   ["all-named.newick"        :newick]
   ["cmt-begin.newick"        :newick]
   ["cmt-begin-words.newick"  :newick]
   ["cmt-end.newick"          :newick]
   ["cmt-end-words.newick"    :newick]
   ["cmt-middle.newick"       :newick]
   ["cmt-middle-words.newick" :newick]
   ["cmt-line-begin.newick"   :newick]
   ["cmt-line-end.newick"     :newick]
   ["dist-all-names.newick"   :newick]
   ["dist-leaf-names.newick"  :newick]
   ["leaf-names.newick"       :newick]
   ["nh-outtree.newick"       :newick]
   ["no-names.newick"         :newick]
   ["leaf-root.newick"        :newick]
   ["big-tree.newick"         :newick]
   ["ninja.newick"            :newick]
   ["test.nexus"              :nexus]
   ["test.phyloxml"           :phyloxml]
   ["treebase-record.xml"     :nexml]
   ["gde.txt"                 :gde]])

(deftest file-type-identification
  (dorun (map test-file-type-identification tests)))
