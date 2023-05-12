package avl;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created with IntelliJ IDEA. User: Antonio J. Nebro Date: 08/07/13
 */
public class AvlTreeTest {

    AvlTree<Integer> avlTree;
    Comparator<?> comparator;

    @BeforeEach
    public void setUp() {
        comparator = Comparator.comparingInt((Integer o) -> o);
        avlTree = new AvlTree<>(comparator);
    }

    @AfterEach
    public void tearDown() {
        avlTree = null;
        comparator = null;
    }

    @Test
    public void testAvlIsEmpty() {
        assertTrue(avlTree.avlIsEmpty());

        avlTree.insertTop(new AvlNode<>(5));
        assertFalse(avlTree.avlIsEmpty());
    }

    @Test
    public void testInsertTop() {
        AvlNode<Integer> node = new AvlNode<>(4);
        avlTree.insertTop(node);
        assertEquals(node, avlTree.getTop());
        String tree = " | 4";
        assertEquals(tree, avlTree.toString());
    }

    @Test
    public void testCompareNodes() {
        AvlNode<Integer> node1 = new AvlNode<>(4);
        AvlNode<Integer> node2 = new AvlNode<>(5);
        AvlNode<Integer> node3 = new AvlNode<>(5);

        assertEquals(-1, avlTree.compareNodes(node1, node2));
        assertEquals(1, avlTree.compareNodes(node3, node1));
        assertEquals(0, avlTree.compareNodes(node2, node3));
    }

  /*
  @Test
  public void testInsertingTheFirstElement() throws Exception {
    AvlNode<Integer> node = new AvlNode<Integer>(6) ;
    avlTree_.insertAvlNode(node);
    assertEquals(node, avlTree_.getTop());
  }
  */

    @Test
    public void testInsertingRightAndLeftElementsJustAfterTop() {
        AvlNode<Integer> node = new AvlNode<>(6);
        avlTree.insertAvlNode(node);
        AvlNode<Integer> nodeLeft = new AvlNode<>(4);
        AvlNode<Integer> nodeRight = new AvlNode<>(9);

        assertEquals(-1, avlTree.searchClosestNode(nodeLeft));
        assertEquals(node, nodeLeft.getClosestNode());
        assertEquals(+1, avlTree.searchClosestNode(nodeRight));
        assertEquals(node, nodeRight.getClosestNode());
        assertEquals(0, avlTree.searchClosestNode(node));

        node.setLeft(nodeLeft);
        node.setRight(nodeRight);
        AvlNode<Integer> nodeRightLeft = new AvlNode<>(7);
        avlTree.searchClosestNode(nodeRightLeft);
        assertEquals(-1, avlTree.searchClosestNode(nodeRightLeft));
        assertEquals(nodeRight, nodeRightLeft.getClosestNode());

        AvlNode<Integer> nodeLeftRight = new AvlNode<>(5);
        assertEquals(1, avlTree.searchClosestNode(nodeLeftRight));
        assertEquals(nodeLeft, nodeLeftRight.getClosestNode());

        String tree = " | 6 | 4 | 9";
        assertEquals(tree, avlTree.toString());
    }

    @Test
    public void testInsertingLeftElement() {
        AvlNode<Integer> node = new AvlNode<>(6);
        avlTree.insertAvlNode(node);
        AvlNode<Integer> nodeLeft = new AvlNode<>(4);
        avlTree.insertAvlNode(nodeLeft);

        assertEquals(node, nodeLeft.getParent());
        assertEquals(nodeLeft, node.getLeft());

        String tree = " | 6 | 4";
        assertEquals(tree, avlTree.toString());
    }

    @Test
    public void testSearchClosestNode() {
        int result;
        AvlNode<Integer> node = new AvlNode<>(7);
        result = avlTree.searchClosestNode(node);
        assertEquals(0, result);
        avlTree.insertAvlNode(node);

        node = new AvlNode<>(4);
        result = avlTree.searchClosestNode(node);
        assertEquals(-1, result);
        avlTree.insertAvlNode(node);

        node = new AvlNode<>(9);
        result = avlTree.searchClosestNode(node);
        assertEquals(1, result);
        avlTree.insertAvlNode(node);

        node = new AvlNode<>(6);
        result = avlTree.searchClosestNode(node);
        assertEquals(1, result);
        avlTree.insertAvlNode(node);

        node = new AvlNode<>(8);
        result = avlTree.searchClosestNode(node);
        assertEquals(-1, result);
        avlTree.insertAvlNode(node);

        String tree = " | 7 | 4 | 6 | 9 | 8";
        assertEquals(tree, avlTree.toString());
    }

    @Test
    public void testInsertingRightElement() {
        AvlNode<Integer> node = new AvlNode<>(6);
        avlTree.insertAvlNode(node);
        AvlNode<Integer> nodeRight = new AvlNode<>(9);
        avlTree.insertAvlNode(nodeRight);

        assertEquals(node, nodeRight.getParent());
        assertEquals(nodeRight, node.getRight());

        String tree = " | 6 | 9";
        assertEquals(tree, avlTree.toString());
    }

    /**
     * Test adding 7 - 4 - 9 - 3 - 5
     */
    @Test
    public void testHeightAndBalanceOfASimpleBalancedTree() {
        AvlNode<Integer> node1, node2, node3, node4, node5;

        node1 = new AvlNode<>(7);
        avlTree.insertAvlNode(node1);
        assertEquals(0, node1.getHeight());
        assertEquals(0, avlTree.getBalance(node1));

        node2 = new AvlNode<>(4);
        avlTree.insertAvlNode(node2);
        assertEquals(0, node2.getHeight());
        assertEquals(1, node1.getHeight());
        assertEquals(-1, avlTree.getBalance(node1));
        assertEquals(0, avlTree.getBalance(node2));

        node3 = new AvlNode<>(9);
        avlTree.insertAvlNode(node3);
        assertEquals(0, node3.getHeight());
        assertEquals(1, node1.getHeight());
        assertEquals(0, avlTree.getBalance(node1));
        assertEquals(0, avlTree.getBalance(node3));

        node4 = new AvlNode<>(3);
        avlTree.insertAvlNode(node4);
        assertEquals(0, node4.getHeight());
        assertEquals(1, node2.getHeight());
        assertEquals(2, node1.getHeight());
        assertEquals(-1, avlTree.getBalance(node2));
        assertEquals(-1, avlTree.getBalance(node1));
        assertEquals(0, avlTree.getBalance(node4));

        node5 = new AvlNode<>(5);
        avlTree.insertAvlNode(node5);
        assertEquals(0, node5.getHeight());
        assertEquals(1, node2.getHeight());
        assertEquals(2, node1.getHeight());
        assertEquals(0, avlTree.getBalance(node2));
        assertEquals(-1, avlTree.getBalance(node1));
        assertEquals(0, avlTree.getBalance(node5));

        String tree = " | 7 | 4 | 3 | 5 | 9";
        assertEquals(tree, avlTree.toString());
    }

    /**
     * Testing adding 7 - 4 - 3
     */
    @Test
    public void testInsertingLeftLeftNodeAndRebalance() {
        AvlNode<Integer> node1, node2, node3;

        node1 = new AvlNode<>(7);
        avlTree.insertAvlNode(node1);
        assertEquals(0, node1.getHeight());
        assertEquals(0, avlTree.getBalance(node1));

        node2 = new AvlNode<>(4);
        avlTree.insertAvlNode(node2);
        assertEquals(0, node2.getHeight());
        assertEquals(1, node1.getHeight());
        assertEquals(-1, avlTree.getBalance(node1));
        assertEquals(0, avlTree.getBalance(node2));

        node3 = new AvlNode<>(3);
        avlTree.insertAvlNode(node3);
        assertEquals(node2, avlTree.getTop());
        assertEquals(node3, node2.getLeft());
        assertEquals(node1, node2.getRight());

        assertEquals(1, avlTree.getTop().getHeight());
        assertEquals(0, avlTree.getTop().getLeft().getHeight());
        assertEquals(0, avlTree.getTop().getRight().getHeight());
        assertEquals(-1, avlTree.height(node1.getLeft()));
        assertEquals(-1, avlTree.height(node1.getRight()));
        assertEquals(-1, avlTree.height(node3.getLeft()));
        assertEquals(-1, avlTree.height(node3.getRight()));

        String tree = " | 4 | 3 | 7";
        assertEquals(tree, avlTree.toString());
    }

    /**
     * Testing adding 7 - 10 - 14
     */
    @Test
    public void testInsertingRightRightNodeAndRebalance() {
        AvlNode<Integer> node1, node2, node3;

        node1 = new AvlNode<>(7);
        avlTree.insertAvlNode(node1);
        assertEquals(0, node1.getHeight());
        assertEquals(0, avlTree.getBalance(node1));

        node2 = new AvlNode<>(10);
        avlTree.insertAvlNode(node2);
        assertEquals(0, node2.getHeight());
        assertEquals(1, node1.getHeight());
        assertEquals(1, avlTree.getBalance(node1));
        assertEquals(0, avlTree.getBalance(node2));

        node3 = new AvlNode<>(14);
        avlTree.insertAvlNode(node3);
        assertEquals(node2, avlTree.getTop());
        assertEquals(node1, node2.getLeft());
        assertEquals(node3, node2.getRight());

        assertEquals(1, avlTree.getTop().getHeight());
        assertEquals(0, avlTree.getTop().getLeft().getHeight());
        assertEquals(0, avlTree.getTop().getRight().getHeight());
        assertEquals(-1, avlTree.height(node1.getLeft()));
        assertEquals(-1, avlTree.height(node1.getRight()));
        assertEquals(-1, avlTree.height(node3.getLeft()));
        assertEquals(-1, avlTree.height(node3.getRight()));

        String tree = " | 10 | 7 | 14";
        assertEquals(tree, avlTree.toString());
    }

    /**
     * Testing adding 7 - 4 - 3 - 2 - 1
     */
    @Test
    public void testInserting7_4_3_2_1() {
        AvlNode<Integer> node1, node2, node3, node4, node5;

        node1 = new AvlNode<>(7);
        node2 = new AvlNode<>(4);
        node3 = new AvlNode<>(3);
        node4 = new AvlNode<>(2);
        node5 = new AvlNode<>(1);

        avlTree.insertAvlNode(node1);
        avlTree.insertAvlNode(node2);
        avlTree.insertAvlNode(node3);
        avlTree.insertAvlNode(node4);
        avlTree.insertAvlNode(node5);

        assertEquals(node2, avlTree.getTop());
        assertEquals(node4, node2.getLeft());
        assertEquals(node1, node2.getRight());
        assertEquals(node5, node4.getLeft());
        assertEquals(node3, node4.getRight());
        assertEquals(0, node1.getHeight());
        assertEquals(2, node2.getHeight());
        assertEquals(1, node4.getHeight());

        String tree = " | 4 | 2 | 1 | 3 | 7";
        assertEquals(tree, avlTree.toString());
    }

    /**
     * Testing adding 7 - 4 - 3 - 2 - 1
     */
    @Test
    public void testInserting7_8_9_10_11() {
        AvlNode<Integer> node1, node2, node3, node4, node5;

        node1 = new AvlNode<>(7);
        node2 = new AvlNode<>(8);
        node3 = new AvlNode<>(9);
        node4 = new AvlNode<>(10);
        node5 = new AvlNode<>(11);

        avlTree.insertAvlNode(node1);
        avlTree.insertAvlNode(node2);
        avlTree.insertAvlNode(node3);
        avlTree.insertAvlNode(node4);
        avlTree.insertAvlNode(node5);

        assertEquals(node2, avlTree.getTop());
        assertEquals(node4, node2.getRight());
        assertEquals(node1, node2.getLeft());
        assertEquals(node5, node4.getRight());
        assertEquals(node3, node4.getLeft());
        assertEquals(2, avlTree.getTop().getHeight());
        assertEquals(1, node4.getHeight());
        assertEquals(0, node1.getHeight());

        String tree = " | 8 | 7 | 10 | 9 | 11";
        assertEquals(tree, avlTree.toString());
    }

    /**
     * Testing adding 7 - 2 - 3
     */
    @Test
    public void testInsertingLeftRightNodeAndRebalance() {
        AvlNode<Integer> node1, node2, node3;

        node1 = new AvlNode<>(7);
        avlTree.insertAvlNode(node1);

        node2 = new AvlNode<>(2);
        avlTree.insertAvlNode(node2);

        node3 = new AvlNode<>(3);
        avlTree.insertAvlNode(node3);

        assertEquals(node3, avlTree.getTop());
        assertEquals(node2, node3.getLeft());
        assertEquals(node1, node3.getRight());

        assertEquals(1, avlTree.getTop().getHeight());
        assertEquals(0, avlTree.getTop().getLeft().getHeight());
        assertEquals(0, avlTree.getTop().getRight().getHeight());
        assertEquals(-1, avlTree.height(node2.getLeft()));
        assertEquals(-1, avlTree.height(node2.getRight()));
        assertEquals(-1, avlTree.height(node1.getLeft()));
        assertEquals(-1, avlTree.height(node1.getRight()));

        String tree = " | 3 | 2 | 7";
        assertEquals(tree, avlTree.toString());
    }

    /**
     * Testing adding 7 - 9 - 8
     */
    @Test
    public void testInsertingRightLeftNodeAndRebalance() {
        AvlNode<Integer> node1, node2, node3;

        node1 = new AvlNode<>(7);
        avlTree.insertAvlNode(node1);

        node2 = new AvlNode<>(9);
        avlTree.insertAvlNode(node2);

        node3 = new AvlNode<>(8);
        avlTree.insertAvlNode(node3);

        assertEquals(node3, avlTree.getTop());
        assertEquals(node1, node3.getLeft());
        assertEquals(node2, node3.getRight());

        assertEquals(1, avlTree.getTop().getHeight());
        assertEquals(0, avlTree.getTop().getLeft().getHeight());
        assertEquals(0, avlTree.getTop().getRight().getHeight());
        assertEquals(-1, avlTree.height(node2.getLeft()));
        assertEquals(-1, avlTree.height(node2.getRight()));
        assertEquals(-1, avlTree.height(node1.getLeft()));
        assertEquals(-1, avlTree.height(node1.getRight()));

        String tree = " | 8 | 7 | 9";
        assertEquals(tree, avlTree.toString());
    }

    @Test
    public void testSearchNode() {
        AvlNode<Integer> node1, node2, node3, node4, node5;

        node1 = new AvlNode<>(7);
        avlTree.insertAvlNode(node1);

        node2 = new AvlNode<>(9);
        avlTree.insertAvlNode(node2);

        node3 = new AvlNode<>(8);
        avlTree.insertAvlNode(node3);

        node4 = new AvlNode<>(2);
        avlTree.insertAvlNode(node4);

        node5 = new AvlNode<>(3);
        avlTree.insertAvlNode(node5);

        assertEquals(node1, avlTree.search(7));
        assertEquals(node2, avlTree.search(9));
        assertEquals(node3, avlTree.search(8));
        assertEquals((Integer) 2, avlTree.searchNode(new AvlNode<>(2)).getItem());
        assertEquals(node4, avlTree.search(2));
        assertEquals(node5, avlTree.search(3));
        assertNull(avlTree.search(14));
        assertNull(avlTree.search(0));

        String tree = " | 8 | 3 | 2 | 7 | 9";
        assertEquals(tree, avlTree.toString());
    }

    @Test
    public void testFindSuccessor() {
        AvlNode<Integer> node;

        node = new AvlNode<>(20);
        avlTree.insertAvlNode(node);

        node = new AvlNode<>(8);
        avlTree.insertAvlNode(node);

        node = new AvlNode<>(22);
        avlTree.insertAvlNode(node);

        node = new AvlNode<>(4);
        avlTree.insertAvlNode(node);

        node = new AvlNode<>(12);
        avlTree.insertAvlNode(node);

        node = new AvlNode<>(24);
        avlTree.insertAvlNode(node);

        node = new AvlNode<>(10);
        avlTree.insertAvlNode(node);

        node = new AvlNode<>(14);
        avlTree.insertAvlNode(node);

        node = avlTree.search(8);
        assertEquals(avlTree.search(10), avlTree.findSuccessor(node));
        node = avlTree.search(10);
        assertEquals(avlTree.search(12), avlTree.findSuccessor(node));
        node = avlTree.search(14);
        assertEquals(avlTree.search(20), avlTree.findSuccessor(node));

        String tree = " | 20 | 8 | 4 | 12 | 10 | 14 | 22 | 24";
        assertEquals(tree, avlTree.toString());
    }

    @Test
    public void testDeletingLeafNodes() {
        AvlNode<Integer> node1, node2, node3, node4, node5;

        node1 = new AvlNode<>(7);
        avlTree.insertAvlNode(node1);

        node2 = new AvlNode<>(9);
        avlTree.insertAvlNode(node2);

        node3 = new AvlNode<>(2);
        avlTree.insertAvlNode(node3);

        node4 = new AvlNode<>(8);
        avlTree.insertAvlNode(node4);

        node5 = new AvlNode<>(3);
        avlTree.insertAvlNode(node5);

        String tree = " | 7 | 2 | 3 | 9 | 8";
        assertEquals(tree, avlTree.toString());

        avlTree.delete(3); // right leaf node
        assertNull(node3.getRight());
        assertEquals(0, node3.getHeight());
        assertEquals(2, avlTree.getTop().getHeight());
        assertEquals(" | 7 | 2 | 9 | 8", avlTree.toString());

        avlTree.delete(8); // left leaf node
        assertNull(node2.getLeft());
        assertEquals(0, node2.getHeight());
        assertEquals(1, avlTree.getTop().getHeight());
        assertEquals(" | 7 | 2 | 9", avlTree.toString());

        avlTree.delete(2); // left leaf node
        assertNull(node1.getLeft());
        assertEquals(1, node1.getHeight());
        assertEquals(" | 7 | 9", avlTree.toString());

        avlTree.delete(9); // right leaf node
        assertNull(node1.getRight());
        assertEquals(0, node1.getHeight());
        assertEquals(" | 7", avlTree.toString());

        avlTree.delete(7); // left leaf node
        assertNull(avlTree.getTop());
        assertEquals("", avlTree.toString());
    }

    @Test
    public void testDeletingNodesWithOneLeaf() {
        AvlNode<Integer> node1, node2, node3, node4, node5;

        node1 = new AvlNode<>(7);
        avlTree.insertAvlNode(node1);

        node2 = new AvlNode<>(9);
        avlTree.insertAvlNode(node2);

        node3 = new AvlNode<>(2);
        avlTree.insertAvlNode(node3);

        node4 = new AvlNode<>(8);
        avlTree.insertAvlNode(node4);

        node5 = new AvlNode<>(3);
        avlTree.insertAvlNode(node5);

        String tree = " | 7 | 2 | 3 | 9 | 8";
        assertEquals(tree, avlTree.toString());

        avlTree.delete(2);
        assertEquals(node3.getItem(), node1.getLeft().getItem());
        assertNull(node3.getRight());
        assertEquals(0, node3.getHeight());
        assertEquals(2, avlTree.getTop().getHeight());
        assertEquals(" | 7 | 3 | 9 | 8", avlTree.toString());

        avlTree.delete(9);
        assertEquals(node2.getItem(), node1.getRight().getItem());
        assertNull(node2.getLeft());
        assertEquals(0, node2.getHeight());
        assertEquals(1, avlTree.getTop().getHeight());
        assertEquals(" | 7 | 3 | 8", avlTree.toString());
    }

    @Test
    public void testDeletingNodesWithTwoLeaves() {
        AvlNode<Integer> node;

        node = new AvlNode<>(20);
        avlTree.insertAvlNode(node);

        node = new AvlNode<>(8);
        avlTree.insertAvlNode(node);

        node = new AvlNode<>(22);
        avlTree.insertAvlNode(node);

        node = new AvlNode<>(4);
        avlTree.insertAvlNode(node);

        node = new AvlNode<>(12);
        avlTree.insertAvlNode(node);

        node = new AvlNode<>(24);
        avlTree.insertAvlNode(node);

        node = new AvlNode<>(10);
        avlTree.insertAvlNode(node);

        node = new AvlNode<>(14);
        avlTree.insertAvlNode(node);

        String expected = " | 20 | 8 | 4 | 12 | 10 | 14 | 22 | 24";
        assertEquals(expected, avlTree.toString());

        avlTree.delete(12);
        node = avlTree.search(8);
        assertEquals(14, (int) node.getRight().getItem());
        assertEquals(" | 20 | 8 | 4 | 14 | 10 | 22 | 24", avlTree.toString());

        avlTree.delete(8);
        assertEquals(10, (int) avlTree.getTop().getLeft().getItem());
        assertEquals(" | 20 | 10 | 4 | 14 | 22 | 24", avlTree.toString());
    }

    @Test
    public void testDeletingAndRebalancing() {
        AvlNode<Integer> node;

        node = new AvlNode<>(20);
        avlTree.insertAvlNode(node);

        node = new AvlNode<>(8);
        avlTree.insertAvlNode(node);

        node = new AvlNode<>(22);
        avlTree.insertAvlNode(node);

        node = new AvlNode<>(4);
        avlTree.insertAvlNode(node);

        node = new AvlNode<>(12);
        avlTree.insertAvlNode(node);

        node = new AvlNode<>(24);
        avlTree.insertAvlNode(node);

        node = new AvlNode<>(10);
        avlTree.insertAvlNode(node);

        node = new AvlNode<>(14);
        avlTree.insertAvlNode(node);

        assertEquals(3, avlTree.getTop().getHeight());

        avlTree.delete(22);
        assertEquals(12, (int) avlTree.getTop().getItem());
        assertEquals(avlTree.search(8), avlTree.getTop().getLeft());
        assertEquals(avlTree.search(20), avlTree.getTop().getRight());
    }

    @Test
    public void testDeletingTopNode() {
        AvlNode<Integer> node;

        node = new AvlNode<>(20);
        avlTree.insertAvlNode(node);

        node = new AvlNode<>(8);
        avlTree.insertAvlNode(node);

        node = new AvlNode<>(22);
        avlTree.insertAvlNode(node);

        node = new AvlNode<>(4);
        avlTree.insertAvlNode(node);

        node = new AvlNode<>(12);
        avlTree.insertAvlNode(node);

        node = new AvlNode<>(24);
        avlTree.insertAvlNode(node);

        node = new AvlNode<>(10);
        avlTree.insertAvlNode(node);

        node = new AvlNode<>(14);
        avlTree.insertAvlNode(node);

        assertEquals(3, avlTree.getTop().getHeight());

        avlTree.delete(20);
        assertEquals(" | 12 | 8 | 4 | 10 | 22 | 14 | 24", avlTree.toString());
    }


}
