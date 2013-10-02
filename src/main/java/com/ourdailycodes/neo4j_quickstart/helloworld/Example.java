package com.ourdailycodes.neo4j_quickstart.helloworld;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;

public class Example {

    public static void main(String[] args) throws InterruptedException {
        GraphDatabaseService graphDb = new GraphDatabaseFactory().newEmbeddedDatabase("database");

        Transaction tx = graphDb.beginTx();
        try {

            Node pokemon = graphDb.createNode();
            pokemon.setProperty("pokemon", "Charmander");

            Node trainer = graphDb.createNode();
            trainer.setProperty("trainer", "Ash");

            Relationship relationship = trainer.createRelationshipTo(pokemon, RelationshipExample.CAPTURES);
            relationship.setProperty("cameFriendly", "yes");
            tx.success();
        } finally {
            tx.close();
            registerShutdownHook(graphDb);
        }

    }

    private static void registerShutdownHook(final GraphDatabaseService graphDb)
    {
        // Registers a shutdown hook for the Neo4j instance so that it
        // shuts down nicely when the VM exits (even if you "Ctrl-C" the
        // running application).
        Runtime.getRuntime().addShutdownHook(new Thread()
        {
            @Override
            public void run()
            {
                graphDb.shutdown();
            }
        });
    }

}
