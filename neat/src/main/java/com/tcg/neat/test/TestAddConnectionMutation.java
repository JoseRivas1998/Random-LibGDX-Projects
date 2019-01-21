package com.tcg.neat.test;

import com.tcg.neat.ConnectionGene;
import com.tcg.neat.Genome;
import com.tcg.neat.InnovationGenerator;
import com.tcg.neat.NodeGene;

import java.util.Random;

/**
 * @author Jose de Jesus Rodriguez Rivas
 * @version 1/20/19
 */
public class TestAddConnectionMutation {

    public static void main(String[] args) {
        InnovationGenerator innovationGenerator = new InnovationGenerator();
        Random random = new Random();
        Genome genome  = new Genome();

        NodeGene input1 = new NodeGene(NodeGene.TYPE.INPUT, 1);
        NodeGene input2 = new NodeGene(NodeGene.TYPE.INPUT, 2);
        NodeGene output = new NodeGene(NodeGene.TYPE.INPUT, 3);

        ConnectionGene con1 = new ConnectionGene(1, 3, 1f, true, 1);
        ConnectionGene con2 = new ConnectionGene(2, 3, 1f, true, 1);

        genome.addNodeGene(input1);
        genome.addNodeGene(input2);
        genome.addNodeGene(output);

        genome.addConnectionGene(con1);
        genome.addConnectionGene(con2);

        GenomePrinter.printGenome(genome, "output/add_con_mut_test_before.png");

        genome.addNodeMutation(random, innovationGenerator);

        GenomePrinter.printGenome(genome, "output/add_con_mut_test_after.png");

    }

}
