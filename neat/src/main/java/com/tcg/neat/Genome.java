package com.tcg.neat;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @author Jose de Jesus Rodriguez Rivas
 * @version 1/20/19
 */
public class Genome {

    private Map<Integer, ConnectionGene> connections;
    private Map<Integer, NodeGene> nodes;

    public Genome() {
        nodes = new HashMap<>();
        connections = new HashMap<>();
    }

    public void addNodeGene(NodeGene gene) {
        nodes.put(gene.getId(), gene);
    }

    public void addConnectionGene(ConnectionGene gene) {
        connections.put(gene.getInnovation(), gene);
    }

    public Map<Integer, ConnectionGene> getConnectionGenes() {
        return new HashMap<>(connections);
    }

    public Map<Integer, NodeGene> getNodeGenes() {
        return new HashMap<>(nodes);
    }

    public void addConnectionMutation(Random r, InnovationGenerator innovationGenerator) {
        NodeGene node1 = nodes.get(r.nextInt(nodes.size()));
        NodeGene node2 = nodes.get(r.nextInt(nodes.size()));

        boolean reversed = false;
        if(node1.getType() == NodeGene.TYPE.HIDDEN && node2.getType() == NodeGene.TYPE.INPUT) {
            reversed = true;
        } else if(node1.getType() == NodeGene.TYPE.OUTPUT && node2.getType() == NodeGene.TYPE.HIDDEN) {
            reversed = true;
        } else if(node1.getType() == NodeGene.TYPE.OUTPUT && node2.getType() == NodeGene.TYPE.INPUT) {
            reversed = true;
        }

        boolean connectionExists = false;
        for (ConnectionGene con : connections.values()) {
            if(con.getInNode() == node1.getId() && con.getOutNode() == node2.getId()) { // existing connection
                connectionExists = true;
                break;
            } else if(con.getInNode() == node2.getId() && con.getOutNode() == node1.getId()) {
                connectionExists = true;
                break;
            }
        }

        if(connectionExists) {
            return;
        }

        ConnectionGene con = new ConnectionGene(
                reversed ? node2.getId() : node1.getId(), // in node
                reversed ? node1.getId() : node2.getId(), // out node
                r.nextFloat() * 2f - 1f, // weight
                true, // expressed
                innovationGenerator.getInnovation()
        );
        connections.put(con.getInnovation(), con);

    }

    public void addNodeMutation(Random r, InnovationGenerator innovationGenerator) {
        ConnectionGene con = connections.get(r.nextInt(connections.size()));

        NodeGene inNode = nodes.get(con.getInNode());
        NodeGene outNode = nodes.get(con.getOutNode());

        con.disable();

        NodeGene newNode = new NodeGene(NodeGene.TYPE.HIDDEN, nodes.size());
        ConnectionGene inToNew = new ConnectionGene(inNode.getId(), newNode.getId(), 1f, true, innovationGenerator.getInnovation());
        ConnectionGene newToOut = new ConnectionGene(newNode.getId(), outNode.getId(), con.getWeight(), true, innovationGenerator.getInnovation());

        nodes.put(newNode.getId(), newNode);
        connections.put(inToNew.getInnovation(), inToNew);
        connections.put(newToOut.getInnovation(), newToOut);

    }

    /**
     * Assum parent 1 is most fit
     * @param parent1
     * @param parent2
     * @return
     */
    public Genome crossover(Genome parent1, Genome parent2, Random r) {
        Genome child = new Genome();
        for (NodeGene parent1Node : parent1.getNodeGenes().values()) {
            child.addNodeGene(parent1Node.copy());
        }

        for (ConnectionGene parent1Node : parent1.getConnectionGenes().values()) {
            if(parent2.getConnectionGenes().containsKey(parent1Node.getInnovation())) {
                // matching gene
                ConnectionGene childConGene = r.nextBoolean() ? parent1Node.copy() : parent2.getConnectionGenes().get(parent1Node.getInnovation()).copy();
                child.addConnectionGene(childConGene);
            } else {
                // disjoint or excess gene
                child.addConnectionGene(parent1Node.copy());
            }
        }

        return child;
    }

}
