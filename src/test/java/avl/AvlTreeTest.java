package avl;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;
import static org.junit.Assert.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.Optional;

import org.junit.Assert;
import org.junit.jupiter.api.*;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.util.ReflectionTestUtils.*;
import org.springframework.util.ReflectionUtils;

/**
 * Created with IntelliJ IDEA. User: Antonio J. Nebro Date: 08/07/13
 */

public class AvlTreeTest {

  AvlTree<Integer> avlTree;
  Comparator<?> comparator;

  @BeforeEach
  public void setUp() throws Exception {
    comparator = Comparator.comparingInt((Integer o) -> o);
    avlTree = new AvlTree(comparator);
  }

  @AfterEach
  public void tearDown() throws Exception {
    avlTree = null;
    comparator = null;
  }

  @Test
  public void testAvlIsEmpty() throws Exception {
    assertTrue("TestAvlIsEmpty", avlTree.avlIsEmpty());

    avlTree.insertTop(new AvlNode(5));
    assertFalse("TestAvlIsEmpty", avlTree.avlIsEmpty());
  }

  @Test
  public void testInsertTop() throws Exception {
    AvlNode<Integer> node = new AvlNode(4);
    avlTree.insertTop(node);
    assertEquals("TestInsertTop", node, avlTree.getTop());
    String tree = " | 4";
    assertEquals("TestInsertTop", tree, avlTree.toString());
  }

  @Test
  public void testCompareNodes() throws Exception {
    AvlNode<Integer> node1 = new AvlNode<Integer>(4);
    AvlNode<Integer> node2 = new AvlNode<Integer>(5);
    AvlNode<Integer> node3 = new AvlNode<Integer>(5);

    assertEquals("testCompareNodes", -1, avlTree.compareNodes(node1, node2));
    assertEquals("testCompareNodes", 1, avlTree.compareNodes(node3, node1));
    assertEquals("testCompareNodes", 0, avlTree.compareNodes(node2, node3));
  }

  /*
  @Test
  public void testInsertingTheFirstElement() throws Exception {
    AvlNode<Integer> node = new AvlNode<Integer>(6) ;
    avlTree_.insertAvlNode(node);
    assertEquals("testInsertingTheFirstElement", node, avlTree_.getTop());
  }
  */

  @Test
  public void testInsertingRightAndLeftElementsJustAfterTop() throws Exception {
    AvlNode<Integer> node = new AvlNode<Integer>(6);
    avlTree.insertAvlNode(node);
    AvlNode<Integer> nodeLeft = new AvlNode<Integer>(4);
    AvlNode<Integer> nodeRight = new AvlNode<Integer>(9);

    assertEquals("testInsertingSecondSmallerElement", -1, avlTree.searchClosestNode(nodeLeft));
    assertEquals("testInsertingSecondSmallerElement", node, nodeLeft.getClosestNode());
    assertEquals("testInsertingSecondSmallerElement", +1, avlTree.searchClosestNode(nodeRight));
    assertEquals("testInsertingSecondSmallerElement", node, nodeRight.getClosestNode());
    assertEquals("testInsertingSecondSmallerElement", 0, avlTree.searchClosestNode(node));

    node.setLeft(nodeLeft);
    node.setRight(nodeRight);
    AvlNode<Integer> nodeRightLeft = new AvlNode<Integer>(7);
    avlTree.searchClosestNode(nodeRightLeft);
    assertEquals("testInsertingSecondSmallerElement", -1,
            avlTree.searchClosestNode(nodeRightLeft));
    assertEquals("testInsertingSecondSmallerElement", nodeRight, nodeRightLeft.getClosestNode());

    AvlNode<Integer> nodeLeftRight = new AvlNode<Integer>(5);
    assertEquals("testInsertingSecondSmallerElement", 1, avlTree.searchClosestNode(nodeLeftRight));
    assertEquals("testInsertingSecondSmallerElement", nodeLeft, nodeLeftRight.getClosestNode());

    String tree = " | 6 | 4 | 9";
    assertEquals("testInsertingSecondSmallerElement", tree, avlTree.toString());
  }

  @Test
  public void testInsertingLeftElement() throws Exception {
    AvlNode<Integer> node = new AvlNode<Integer>(6);
    avlTree.insertAvlNode(node);
    AvlNode<Integer> nodeLeft = new AvlNode<Integer>(4);
    avlTree.insertAvlNode(nodeLeft);

    assertEquals("testInsertingLeftElement", node, nodeLeft.getParent());
    assertEquals("testInsertingLeftElement", nodeLeft, node.getLeft());

    String tree = " | 6 | 4";
    assertEquals("testInsertingLeftElement", tree, avlTree.toString());
  }

  @Test
  public void testSearchClosestNode() throws Exception {
    int result;
    AvlNode<Integer> node = new AvlNode<Integer>(7);
    result = avlTree.searchClosestNode(node);
    assertEquals("testSearchClosestNode", 0, result);
    avlTree.insertAvlNode(node);

    node = new AvlNode<Integer>(4);
    result = avlTree.searchClosestNode(node);
    assertEquals("testSearchClosestNode", -1, result);
    avlTree.insertAvlNode(node);

    node = new AvlNode<Integer>(9);
    result = avlTree.searchClosestNode(node);
    assertEquals("testSearchClosestNode", 1, result);
    avlTree.insertAvlNode(node);

    node = new AvlNode<Integer>(6);
    result = avlTree.searchClosestNode(node);
    assertEquals("testSearchClosestNode", 1, result);
    avlTree.insertAvlNode(node);

    node = new AvlNode<Integer>(8);
    result = avlTree.searchClosestNode(node);
    assertEquals("testSearchClosestNode", -1, result);
    avlTree.insertAvlNode(node);

    String tree = " | 7 | 4 | 6 | 9 | 8";
    assertEquals("testSearchClosestNode", tree, avlTree.toString());
  }

  @Test
  public void testInsertingRightElement() throws Exception {
    AvlNode<Integer> node = new AvlNode<Integer>(6);
    avlTree.insertAvlNode(node);
    AvlNode<Integer> nodeRight = new AvlNode<Integer>(9);
    avlTree.insertAvlNode(nodeRight);

    assertEquals("testInsertingRightElement", node, nodeRight.getParent());
    assertEquals("testInsertingRightElement", nodeRight, node.getRight());

    String tree = " | 6 | 9";
    assertEquals("testInsertingRightElement", tree, avlTree.toString());
  }

  /**
   * Test adding 7 - 4 - 9 - 3 - 5
   *
   * @throws Exception
   */
  @Test
  public void testHeightAndBalanceOfASimpleBalancedTree() throws Exception {
    AvlNode<Integer> node1, node2, node3, node4, node5;

    node1 = new AvlNode<Integer>(7);
    avlTree.insertAvlNode(node1);
    assertEquals("testHeightOfASimpleBalancedTree", 0, node1.getHeight());
    assertEquals("testHeightOfASimpleBalancedTree", 0, avlTree.getBalance(node1));

    node2 = new AvlNode<Integer>(4);
    avlTree.insertAvlNode(node2);
    assertEquals("testHeightOfASimpleBalancedTree", 0, node2.getHeight());
    assertEquals("testHeightOfASimpleBalancedTree", 1, node1.getHeight());
    assertEquals("testHeightOfASimpleBalancedTree", -1, avlTree.getBalance(node1));
    assertEquals("testHeightOfASimpleBalancedTree", 0, avlTree.getBalance(node2));

    node3 = new AvlNode<Integer>(9);
    avlTree.insertAvlNode(node3);
    assertEquals("testHeightOfASimpleBalancedTree", 0, node3.getHeight());
    assertEquals("testHeightOfASimpleBalancedTree", 1, node1.getHeight());
    assertEquals("testHeightOfASimpleBalancedTree", 0, avlTree.getBalance(node1));
    assertEquals("testHeightOfASimpleBalancedTree", 0, avlTree.getBalance(node3));

    node4 = new AvlNode<Integer>(3);
    avlTree.insertAvlNode(node4);
    assertEquals("testHeightOfASimpleBalancedTree", 0, node4.getHeight());
    assertEquals("testHeightOfASimpleBalancedTree", 1, node2.getHeight());
    assertEquals("testHeightOfASimpleBalancedTree", 2, node1.getHeight());
    assertEquals("testHeightOfASimpleBalancedTree", -1, avlTree.getBalance(node2));
    assertEquals("testHeightOfASimpleBalancedTree", -1, avlTree.getBalance(node1));
    assertEquals("testHeightOfASimpleBalancedTree", 0, avlTree.getBalance(node4));

    node5 = new AvlNode<Integer>(5);
    avlTree.insertAvlNode(node5);
    assertEquals("testHeightOfASimpleBalancedTree", 0, node5.getHeight());
    assertEquals("testHeightOfASimpleBalancedTree", 1, node2.getHeight());
    assertEquals("testHeightOfASimpleBalancedTree", 2, node1.getHeight());
    assertEquals("testHeightOfASimpleBalancedTree", 0, avlTree.getBalance(node2));
    assertEquals("testHeightOfASimpleBalancedTree", -1, avlTree.getBalance(node1));
    assertEquals("testHeightOfASimpleBalancedTree", 0, avlTree.getBalance(node5));

    String tree = " | 7 | 4 | 3 | 5 | 9";
    assertEquals("testHeightOfASimpleBalancedTree", tree, avlTree.toString());
  }

  /**
   * Testing adding 7 - 4 - 3
   *
   * @throws Exception
   */
  @Test
  public void testInsertingLeftLeftNodeAndRebalance() throws Exception {
    AvlNode<Integer> node1, node2, node3, node4, node5;

    node1 = new AvlNode<Integer>(7);
    avlTree.insertAvlNode(node1);
    assertEquals("testInsertingLeftLeftNodeAndRebalance", 0, node1.getHeight());
    assertEquals("testInsertingLeftLeftNodeAndRebalance", 0, avlTree.getBalance(node1));

    node2 = new AvlNode<Integer>(4);
    avlTree.insertAvlNode(node2);
    assertEquals("testInsertingLeftLeftNodeAndRebalance", 0, node2.getHeight());
    assertEquals("testInsertingLeftLeftNodeAndRebalance", 1, node1.getHeight());
    assertEquals("testInsertingLeftLeftNodeAndRebalance", -1, avlTree.getBalance(node1));
    assertEquals("testInsertingLeftLeftNodeAndRebalance", 0, avlTree.getBalance(node2));

    node3 = new AvlNode<Integer>(3);
    avlTree.insertAvlNode(node3);
    assertEquals("testInsertingLeftLeftNodeAndRebalance", node2, avlTree.getTop());
    assertEquals("testInsertingLeftLeftNodeAndRebalance", node3, node2.getLeft());
    assertEquals("testInsertingLeftLeftNodeAndRebalance", node1, node2.getRight());

    assertEquals("testInsertingLeftLeftNodeAndRebalance", 1, avlTree.getTop().getHeight());
    assertEquals("testInsertingLeftLeftNodeAndRebalance", 0,
            avlTree.getTop().getLeft().getHeight());
    assertEquals("testInsertingLeftLeftNodeAndRebalance", 0,
            avlTree.getTop().getRight().getHeight());
    assertEquals("testInsertingLeftLeftNodeAndRebalance", -1, avlTree.height(node1.getLeft()));
    assertEquals("testInsertingLeftLeftNodeAndRebalance", -1, avlTree.height(node1.getRight()));
    assertEquals("testInsertingLeftLeftNodeAndRebalance", -1, avlTree.height(node3.getLeft()));
    assertEquals("testInsertingLeftLeftNodeAndRebalance", -1, avlTree.height(node3.getRight()));

    String tree = " | 4 | 3 | 7";
    assertEquals("testInsertingLeftLeftNodeAndRebalance", tree, avlTree.toString());
  }

  /**
   * Testing adding 7 - 10 - 14
   *
   * @throws Exception
   */
  @Test
  public void testInsertingRightRightNodeAndRebalance() throws Exception {
    AvlNode<Integer> node1, node2, node3, node4, node5;

    node1 = new AvlNode<Integer>(7);
    avlTree.insertAvlNode(node1);
    assertEquals("testInsertingRightRightNodeAndRebalance", 0, node1.getHeight());
    assertEquals("testInsertingRightRightNodeAndRebalance", 0, avlTree.getBalance(node1));

    node2 = new AvlNode<Integer>(10);
    avlTree.insertAvlNode(node2);
    assertEquals("testInsertingRightRightNodeAndRebalance", 0, node2.getHeight());
    assertEquals("testInsertingRightRightNodeAndRebalance", 1, node1.getHeight());
    assertEquals("testInsertingRightRightNodeAndRebalance", 1, avlTree.getBalance(node1));
    assertEquals("testInsertingRightRightNodeAndRebalance", 0, avlTree.getBalance(node2));

    node3 = new AvlNode<Integer>(14);
    avlTree.insertAvlNode(node3);
    assertEquals("testInsertingRightRightNodeAndRebalance", node2, avlTree.getTop());
    assertEquals("testInsertingRightRightNodeAndRebalance", node1, node2.getLeft());
    assertEquals("testInsertingRightRightNodeAndRebalance", node3, node2.getRight());

    assertEquals("testInsertingRightRightNodeAndRebalance", 1, avlTree.getTop().getHeight());
    assertEquals("testInsertingRightRightNodeAndRebalance", 0,
            avlTree.getTop().getLeft().getHeight());
    assertEquals("testInsertingRightRightNodeAndRebalance", 0,
            avlTree.getTop().getRight().getHeight());
    assertEquals("testInsertingRightRightNodeAndRebalance", -1, avlTree.height(node1.getLeft()));
    assertEquals("testInsertingRightRightNodeAndRebalance", -1, avlTree.height(node1.getRight()));
    assertEquals("testInsertingRightRightNodeAndRebalance", -1, avlTree.height(node3.getLeft()));
    assertEquals("testInsertingRightRightNodeAndRebalance", -1, avlTree.height(node3.getRight()));

    String tree = " | 10 | 7 | 14";
    assertEquals("testInsertingRightRightNodeAndRebalance", tree, avlTree.toString());
  }

  /**
   * Testing adding 7 - 4 - 3 - 2 - 1
   *
   * @throws Exception
   */
  @Test
  public void testInserting7_4_3_2_1() throws Exception {
    AvlNode<Integer> node1, node2, node3, node4, node5;

    node1 = new AvlNode<Integer>(7);
    node2 = new AvlNode<Integer>(4);
    node3 = new AvlNode<Integer>(3);
    node4 = new AvlNode<Integer>(2);
    node5 = new AvlNode<Integer>(1);

    avlTree.insertAvlNode(node1);
    avlTree.insertAvlNode(node2);
    avlTree.insertAvlNode(node3);
    avlTree.insertAvlNode(node4);
    avlTree.insertAvlNode(node5);

    assertEquals("testInserting7_4_3_2_1", node2, avlTree.getTop());
    assertEquals("testInserting7_4_3_2_1", node4, node2.getLeft());
    assertEquals("testInserting7_4_3_2_1", node1, node2.getRight());
    assertEquals("testInserting7_4_3_2_1", node5, node4.getLeft());
    assertEquals("testInserting7_4_3_2_1", node3, node4.getRight());
    assertEquals("testInserting7_4_3_2_1", 0, node1.getHeight());
    assertEquals("testInserting7_4_3_2_1", 2, node2.getHeight());
    assertEquals("testInserting7_4_3_2_1", 1, node4.getHeight());

    String tree = " | 4 | 2 | 1 | 3 | 7";
    assertEquals("testInserting7_4_3_2_1", tree, avlTree.toString());
  }

  /**
   * Testing adding 7 - 4 - 3 - 2 - 1
   *
   * @throws Exception
   */
  @Test
  public void testInserting7_8_9_10_11() throws Exception {
    AvlNode<Integer> node1, node2, node3, node4, node5;

    node1 = new AvlNode<Integer>(7);
    node2 = new AvlNode<Integer>(8);
    node3 = new AvlNode<Integer>(9);
    node4 = new AvlNode<Integer>(10);
    node5 = new AvlNode<Integer>(11);

    avlTree.insertAvlNode(node1);
    avlTree.insertAvlNode(node2);
    avlTree.insertAvlNode(node3);
    avlTree.insertAvlNode(node4);
    avlTree.insertAvlNode(node5);

    assertEquals("testInserting7_8_9_10_11", node2, avlTree.getTop());
    assertEquals("testInserting7_8_9_10_11", node4, node2.getRight());
    assertEquals("testInserting7_8_9_10_11", node1, node2.getLeft());
    assertEquals("testInserting7_8_9_10_11", node5, node4.getRight());
    assertEquals("testInserting7_8_9_10_11", node3, node4.getLeft());
    assertEquals("testInserting7_8_9_10_11", 2, avlTree.getTop().getHeight());
    assertEquals("testInserting7_8_9_10_11", 1, node4.getHeight());
    assertEquals("testInserting7_8_9_10_11", 0, node1.getHeight());

    String tree = " | 8 | 7 | 10 | 9 | 11";
    assertEquals("testInserting7_8_9_10_11", tree, avlTree.toString());
  }

  /**
   * Testing adding 7 - 2 - 3
   *
   * @throws Exception
   */
  @Test
  public void testInsertingLeftRightNodeAndRebalance() throws Exception {
    AvlNode<Integer> node1, node2, node3;

    node1 = new AvlNode<Integer>(7);
    avlTree.insertAvlNode(node1);

    node2 = new AvlNode<Integer>(2);
    avlTree.insertAvlNode(node2);

    node3 = new AvlNode<Integer>(3);
    avlTree.insertAvlNode(node3);

    assertEquals("testInsertingLeftRightNodeAndRebalance", node3, avlTree.getTop());
    assertEquals("testInsertingLeftRightNodeAndRebalance", node2, node3.getLeft());
    assertEquals("testInsertingLeftRightNodeAndRebalance", node1, node3.getRight());

    assertEquals("testInsertingLeftRightNodeAndRebalance", 1, avlTree.getTop().getHeight());
    assertEquals("testInsertingLeftRightNodeAndRebalance", 0,
            avlTree.getTop().getLeft().getHeight());
    assertEquals("testInsertingLeftRightNodeAndRebalance", 0,
            avlTree.getTop().getRight().getHeight());
    assertEquals("testInsertingLeftRightNodeAndRebalance", -1, avlTree.height(node2.getLeft()));
    assertEquals("testInsertingLeftRightNodeAndRebalance", -1, avlTree.height(node2.getRight()));
    assertEquals("testInsertingLeftRightNodeAndRebalance", -1, avlTree.height(node1.getLeft()));
    assertEquals("testInsertingLeftRightNodeAndRebalance", -1, avlTree.height(node1.getRight()));

    String tree = " | 3 | 2 | 7";
    assertEquals("testInsertingLeftRightNodeAndRebalance", tree, avlTree.toString());
  }

  /**
   * Testing adding 7 - 9 - 8
   *
   * @throws Exception
   */
  @Test
  public void testInsertingRightLeftNodeAndRebalance() throws Exception {
    AvlNode<Integer> node1, node2, node3;

    node1 = new AvlNode<Integer>(7);
    avlTree.insertAvlNode(node1);

    node2 = new AvlNode<Integer>(9);
    avlTree.insertAvlNode(node2);

    node3 = new AvlNode<Integer>(8);
    avlTree.insertAvlNode(node3);

    assertEquals("testInsertingRightLeftNodeAndRebalance", node3, avlTree.getTop());
    assertEquals("testInsertingRightLeftNodeAndRebalance", node1, node3.getLeft());
    assertEquals("testInsertingRightLeftNodeAndRebalance", node2, node3.getRight());

    assertEquals("testInsertingRightLeftNodeAndRebalance", 1, avlTree.getTop().getHeight());
    assertEquals("testInsertingRightLeftNodeAndRebalance", 0,
            avlTree.getTop().getLeft().getHeight());
    assertEquals("testInsertingRightLeftNodeAndRebalance", 0,
            avlTree.getTop().getRight().getHeight());
    assertEquals("testInsertingRightLeftNodeAndRebalance", -1, avlTree.height(node2.getLeft()));
    assertEquals("testInsertingRightLeftNodeAndRebalance", -1, avlTree.height(node2.getRight()));
    assertEquals("testInsertingRightLeftNodeAndRebalance", -1, avlTree.height(node1.getLeft()));
    assertEquals("testInsertingRightLeftNodeAndRebalance", -1, avlTree.height(node1.getRight()));

    String tree = " | 8 | 7 | 9";
    assertEquals("testInsertingRightLeftNodeAndRebalance", tree, avlTree.toString());
  }

  @Test
  public void testSearchNode() throws Exception {
    AvlNode<Integer> node1, node2, node3, node4, node5;

    node1 = new AvlNode<Integer>(7);
    avlTree.insertAvlNode(node1);

    node2 = new AvlNode<Integer>(9);
    avlTree.insertAvlNode(node2);

    node3 = new AvlNode<Integer>(8);
    avlTree.insertAvlNode(node3);

    node4 = new AvlNode<Integer>(2);
    avlTree.insertAvlNode(node4);

    node5 = new AvlNode<Integer>(3);
    avlTree.insertAvlNode(node5);

    assertEquals("testSearchNode", node1, avlTree.search(7));
    assertEquals("testSearchNode", node2, avlTree.search(9));
    assertEquals("testSearchNode", node3, avlTree.search(8));
    assertEquals("testSearchNode", (Integer) 2,
            avlTree.searchNode(new AvlNode<Integer>(2)).getItem());
    assertEquals("testSearchNode", node4, avlTree.search(2));
    assertEquals("testSearchNode", node5, avlTree.search(3));
    assertNull("testInsertNode", avlTree.search(14));
    assertNull("testSearchNode", avlTree.search(0));

    String tree = " | 8 | 3 | 2 | 7 | 9";
    assertEquals("testSearchNode", tree, avlTree.toString());
  }

  @Test
  public void testFindSuccessor() throws Exception {
    AvlNode<Integer> node;

    node = new AvlNode<Integer>(20);
    avlTree.insertAvlNode(node);

    node = new AvlNode<Integer>(8);
    avlTree.insertAvlNode(node);

    node = new AvlNode<Integer>(22);
    avlTree.insertAvlNode(node);

    node = new AvlNode<Integer>(4);
    avlTree.insertAvlNode(node);

    node = new AvlNode<Integer>(12);
    avlTree.insertAvlNode(node);

    node = new AvlNode<Integer>(24);
    avlTree.insertAvlNode(node);

    node = new AvlNode<Integer>(10);
    avlTree.insertAvlNode(node);

    node = new AvlNode<Integer>(14);
    avlTree.insertAvlNode(node);

    node = avlTree.search(8);
    assertEquals("testFindSuccessor", avlTree.search(10), avlTree.findSuccessor(node));
    node = avlTree.search(10);
    assertEquals("testFindSuccessor", avlTree.search(12), avlTree.findSuccessor(node));
    node = avlTree.search(14);
    assertEquals("testFindSuccessor", avlTree.search(20), avlTree.findSuccessor(node));

    String tree = " | 20 | 8 | 4 | 12 | 10 | 14 | 22 | 24";
    assertEquals("testSearchNode", tree, avlTree.toString());
  }

  @Test
  public void testDeletingLeafNodes() throws Exception {
    AvlNode<Integer> node1, node2, node3, node4, node5;

    node1 = new AvlNode<Integer>(7);
    avlTree.insertAvlNode(node1);

    node2 = new AvlNode<Integer>(9);
    avlTree.insertAvlNode(node2);

    node3 = new AvlNode<Integer>(2);
    avlTree.insertAvlNode(node3);

    node4 = new AvlNode<Integer>(8);
    avlTree.insertAvlNode(node4);

    node5 = new AvlNode<Integer>(3);
    avlTree.insertAvlNode(node5);

    String tree = " | 7 | 2 | 3 | 9 | 8";
    assertEquals("testDeletingLeafNodes", tree, avlTree.toString());

    avlTree.delete(3); // right leaf node
    assertEquals("testDeletingLeafNodes", null, node3.getRight());
    assertEquals("testDeletingLeafNodes", 0, node3.getHeight());
    assertEquals("testDeletingLeafNodes", 2, avlTree.getTop().getHeight());
    assertEquals("testDeletingLeafNodes", " | 7 | 2 | 9 | 8", avlTree.toString());

    avlTree.delete(8); // left leaf node
    assertEquals("testDeletingLeafNodes", null, node2.getLeft());
    assertEquals("testDeletingLeafNodes", 0, node2.getHeight());
    assertEquals("testDeletingLeafNodes", 1, avlTree.getTop().getHeight());
    assertEquals("testDeletingLeafNodes", " | 7 | 2 | 9", avlTree.toString());

    avlTree.delete(2); // left leaf node
    assertEquals("testDeletingLeafNodes", null, node1.getLeft());
    assertEquals("testDeletingLeafNodes", 1, node1.getHeight());
    assertEquals("testDeletingLeafNodes", " | 7 | 9", avlTree.toString());

    avlTree.delete(9); // right leaf node
    assertEquals("testDeletingLeafNodes", null, node1.getRight());
    assertEquals("testDeletingLeafNodes", 0, node1.getHeight());
    assertEquals("testDeletingLeafNodes", " | 7", avlTree.toString());

    avlTree.delete(7); // left leaf node
    assertEquals("testDeletingLeafNodes", null, avlTree.getTop());
    assertEquals("testDeletingLeafNodes", "", avlTree.toString());
  }

  @Test
  public void testDeletingNodesWithOneLeaf() throws Exception {
    AvlNode<Integer> node1, node2, node3, node4, node5;

    node1 = new AvlNode<Integer>(7);
    avlTree.insertAvlNode(node1);

    node2 = new AvlNode<Integer>(9);
    avlTree.insertAvlNode(node2);

    node3 = new AvlNode<Integer>(2);
    avlTree.insertAvlNode(node3);

    node4 = new AvlNode<Integer>(8);
    avlTree.insertAvlNode(node4);

    node5 = new AvlNode<Integer>(3);
    avlTree.insertAvlNode(node5);

    String tree = " | 7 | 2 | 3 | 9 | 8";
    assertEquals("testDeletingNodesWithOneLeaf", tree, avlTree.toString());

    avlTree.delete(2);
    assertEquals("testDeletingNodesWithOneLeaf", node3.getItem(), node1.getLeft().getItem());
    assertEquals("testDeletingNodesWithOneLeaf", null, node3.getRight());
    assertEquals("testDeletingNodesWithOneLeaf", 0, node3.getHeight());
    assertEquals("testDeletingNodesWithOneLeaf", 2, avlTree.getTop().getHeight());
    assertEquals("testDeletingNodesWithOneLeaf", " | 7 | 3 | 9 | 8", avlTree.toString());

    avlTree.delete(9);
    assertEquals("testDeletingNodesWithOneLeaf", node2.getItem(), node1.getRight().getItem());
    assertEquals("testDeletingNodesWithOneLeaf", null, node2.getLeft());
    assertEquals("testDeletingNodesWithOneLeaf", 0, node2.getHeight());
    assertEquals("testDeletingNodesWithOneLeaf", 1, avlTree.getTop().getHeight());
    assertEquals("testDeletingNodesWithOneLeaf", " | 7 | 3 | 8", avlTree.toString());
  }

  @Test
  public void testDeletingNodesWithTwoLeaves() throws Exception {
    AvlNode<Integer> node;

    node = new AvlNode<Integer>(20);
    avlTree.insertAvlNode(node);

    node = new AvlNode<Integer>(8);
    avlTree.insertAvlNode(node);

    node = new AvlNode<Integer>(22);
    avlTree.insertAvlNode(node);

    node = new AvlNode<Integer>(4);
    avlTree.insertAvlNode(node);

    node = new AvlNode<Integer>(12);
    avlTree.insertAvlNode(node);

    node = new AvlNode<Integer>(24);
    avlTree.insertAvlNode(node);

    node = new AvlNode<Integer>(10);
    avlTree.insertAvlNode(node);

    node = new AvlNode<Integer>(14);
    avlTree.insertAvlNode(node);

    String expected = " | 20 | 8 | 4 | 12 | 10 | 14 | 22 | 24";
    assertEquals("testDeletingNodesWithTwoLeaves", expected, avlTree.toString());

    avlTree.delete(12);
    node = avlTree.search(8);
    assertEquals("testDeletingNodesWithTwoLeaves", 14, (int) node.getRight().getItem());
    assertEquals("testDeletingNodesWithTwoLeaves", " | 20 | 8 | 4 | 14 | 10 | 22 | 24",
            avlTree.toString());

    avlTree.delete(8);
    assertEquals("testDeletingNodesWithTwoLeaves", 10, (int) avlTree.getTop().getLeft().getItem());
    assertEquals("testDeletingNodesWithTwoLeaves", " | 20 | 10 | 4 | 14 | 22 | 24",
            avlTree.toString());
  }

  @Test
  public void testDeletingAndRebalancing() throws Exception {
    AvlNode<Integer> node;

    node = new AvlNode<Integer>(20);
    avlTree.insertAvlNode(node);

    node = new AvlNode<Integer>(8);
    avlTree.insertAvlNode(node);

    node = new AvlNode<Integer>(22);
    avlTree.insertAvlNode(node);

    node = new AvlNode<Integer>(4);
    avlTree.insertAvlNode(node);

    node = new AvlNode<Integer>(12);
    avlTree.insertAvlNode(node);

    node = new AvlNode<Integer>(24);
    avlTree.insertAvlNode(node);

    node = new AvlNode<Integer>(10);
    avlTree.insertAvlNode(node);

    node = new AvlNode<Integer>(14);
    avlTree.insertAvlNode(node);

    assertEquals("testDeletingDeepLeafNode", 3, avlTree.getTop().getHeight());

    avlTree.delete(22);
    assertEquals("testDeletingDeepLeafNode", 12, (int) avlTree.getTop().getItem());
    assertEquals("testDeletingDeepLeafNode", avlTree.search(8), avlTree.getTop().getLeft());
    assertEquals("testDeletingDeepLeafNode", avlTree.search(20), avlTree.getTop().getRight());
  }

  @Test
  public void testDeletingTopNode() throws Exception {
    AvlNode<Integer> node;

    node = new AvlNode<Integer>(20);
    avlTree.insertAvlNode(node);

    node = new AvlNode<Integer>(8);
    avlTree.insertAvlNode(node);

    node = new AvlNode<Integer>(22);
    avlTree.insertAvlNode(node);

    node = new AvlNode<Integer>(4);
    avlTree.insertAvlNode(node);

    node = new AvlNode<Integer>(12);
    avlTree.insertAvlNode(node);

    node = new AvlNode<Integer>(24);
    avlTree.insertAvlNode(node);

    node = new AvlNode<Integer>(10);
    avlTree.insertAvlNode(node);

    node = new AvlNode<Integer>(14);
    avlTree.insertAvlNode(node);

    assertEquals("testDeletingTopNode", 3, avlTree.getTop().getHeight());

    avlTree.delete(20);
    assertEquals("testDeletingTopNode", " | 12 | 8 | 4 | 10 | 22 | 14 | 24", avlTree.toString());
  }

  //-------------------------------------PRUEBAS DE CAJA NEGRA-------------------------------------------------------------------

  /*
    Search
      Con AVLNode nulo
      Con AVLNode en el árbol en la raíz
      Con AVLNode en el árbol en la rama izquierda
      Con AVLNode en el árbol en la rama derecha
      Con AVLNode no en el árbol
   */
  @Nested
  @DisplayName("Tests para search")
  class TestSearch {

    @Test
    @DisplayName("Search con AVLNode null")
    public void Given_AVLTree_When_SearchNull_Then_ReturnNull() {
      assertEquals(null, avlTree.search(null));
    }

    @Test
    @DisplayName("Search con AVLNode en la raiz del arbol")
    public void Given_AVLTreeWithElement3InRoot_When_Search3_Then_ReturnAVLNodeWithElement3() {
      avlTree.insert(3);
      assertEquals(3, (int) avlTree.search(3).getItem());
      assertEquals(null, avlTree.search(3).getParent());
      assertEquals(false, avlTree.search(3).hasLeft());
      assertEquals(false, avlTree.search(3).hasRight());
    }

    @Test
    @DisplayName("Search con AVLNode en la rama izquierda del arbol")
    public void Given_AVLTreeWithElement3InLeftBranch_When_Search3_Then_ReturnAVLNodeWithElement3() {
      avlTree.insert(1);
      avlTree.insert(0);
      assertEquals(0, (int) avlTree.search(0).getItem());
      assertEquals(1, (int) avlTree.search(0).getParent().getItem());
      assertEquals(0, (int) avlTree.search(0).getParent().getLeft().getItem());
      assertEquals(null, avlTree.search(0).getParent().getRight());
      assertEquals(false, avlTree.search(0).hasLeft());
      assertEquals(false, avlTree.search(0).hasRight());
    }

    @Test
    @DisplayName("Search con AVLNode en la rama derecha del arbol")
    public void Given_AVLTreeWithElement3InRightBranch_When_Search3_Then_ReturnAVLNodeWithElement3() {
      avlTree.insert(1);
      avlTree.insert(3);
      assertEquals(3, (int) avlTree.search(3).getItem());
      assertEquals(1, (int) avlTree.search(3).getParent().getItem());
      assertEquals(3, (int) avlTree.search(3).getParent().getRight().getItem());
      assertEquals(null, avlTree.search(3).getParent().getLeft());
      assertEquals(false, avlTree.search(3).hasLeft());
      assertEquals(false, avlTree.search(3).hasRight());
    }

    @Test
    @DisplayName("Search con AvlNode no incluido en el arbol")
    public void Given_AVLTreeWithNoElementsInside_When_Search3_Then_ReturnNull() {
      assertEquals(null, avlTree.search(3));
    }

  }

  /* SearchNode
      Con AVLNode nulo
      Con AVLNode en el árbol en la raíz
      Con AVLNode en el árbol en la rama izquierda
      Con AVLNode en el árbol en la rama derecha
      Con AVLNode no en el árbol
  */
  @Nested
  @DisplayName("Tests para searchNode")
  class TestSearchNode {


    @Test
    @DisplayName("SearchNode con AVLNode null")
    public void Given_AVLTree_When_SearchNodeNull_Then_ReturnNull() {
      assertEquals(null, avlTree.searchNode(null));
    }

    @Test
    @DisplayName("SearchNode con AVLNode en la raíz del arbol")
    public void Given_AVLTreeWithElement3InRoot_When_SearchNode3_Then_ReturnAVLNodeWithElement3() {
      avlTree.insert(3);

      AvlNode node = new AvlNode(3);

      assertEquals(3, (int) avlTree.searchNode(node).getItem());
      assertEquals(null, avlTree.searchNode(node).getParent());
      assertEquals(false, avlTree.searchNode(node).hasLeft());
      assertEquals(false, avlTree.searchNode(node).hasRight());
    }

    @Test
    @DisplayName("SearchNode con AVLNode en la rama izquierda del arbol")
    public void Given_AVLTreeWithElement0InLeftBranch_When_SearchNode0_Then_ReturnAVLNodeWithElement0() {
      avlTree.insert(1);
      avlTree.insert(0);

      AvlNode node = new AvlNode(0);
      assertEquals(0, (int) avlTree.searchNode(node).getItem());
      assertEquals(1, (int) avlTree.searchNode(node).getParent().getItem());
      assertEquals(0, (int) avlTree.searchNode(node).getParent().getLeft().getItem());
      assertEquals(null, avlTree.searchNode(node).getParent().getRight());
      assertEquals(false, avlTree.searchNode(node).hasLeft());
      assertEquals(false, avlTree.searchNode(node).hasRight());
    }

    @Test
    @DisplayName("SearchNode con AVLNode en la rama derecha del arbol")
    public void Given_AVLTreeWithElement3InRightBranch_When_SearchNode3_Then_ReturnAVLNodeWithElement3() {
      avlTree.insert(1);
      avlTree.insert(3);
      AvlNode node = new AvlNode(3);
      assertEquals(3, (int) avlTree.searchNode(node).getItem());
      assertEquals(1, (int) avlTree.searchNode(node).getParent().getItem());
      assertEquals(3, (int) avlTree.searchNode(node).getParent().getRight().getItem());
      assertEquals(null, avlTree.searchNode(node).getParent().getLeft());
      assertEquals(false, avlTree.searchNode(node).hasLeft());
      assertEquals(false, avlTree.searchNode(node).hasRight());
    }

    @Test
    @DisplayName("SearchNode con AvlNode no incluido en el arbol")
    public void Given_AVLTreeWithNoElementsInside_When_SearchNode3_Then_ReturnNull() {
      AvlNode node = new AvlNode(3);

      assertEquals(null, avlTree.searchNode(node));
    }
  }

  /*
    DeleteNode
        Con AVLNode nulo
        Con AVLNode en el árbol en la raíz
        Con AVLNode en el árbol en la rama izquierda
        Con AVLNode en el árbol en la rama derecha
        Con AVLNode en el árbol en una rama, borrar padre
        Con AVLNode no en el árbol
   */
  @Nested
  @DisplayName("Tests para deleteNode")
  class TestDeleteNode {
    @Test
    @DisplayName("DeleteNode con AVLNode null")
    public void Given_AVLTree_When_DeleteNodeNull_Then_NothingHappens() {
      avlTree.insert(3);
      Assertions.assertThrows(Exception.class, () -> avlTree.delete(null));
      assertEquals(3, (int) avlTree.search(3).getItem());
    }

    @Test
    @DisplayName("DeleteNode con AVLNode en la raíz del arbol")
    public void Given_AVLTreeWithElement1InRoot_When_DeleteNode1_Then_AVLNode1IsNotInTree() {
      avlTree.insert(1);
      AvlNode node = new AvlNode(1);

      avlTree.deleteNode(node);

      assertEquals(null, avlTree.searchNode(node));
    }

    @Test
    @DisplayName("DeleteNode con AVLNode en la rama izquierda del arbol")
    public void Given_AVLTreeWithElement0InLeftBranch_When_DeleteNode0_Then_AVLNode0IsNotInTree() {
      avlTree.insert(1);
      avlTree.insert(0);

      AvlNode node = new AvlNode(0);

      avlTree.deleteNode(node);

      assertEquals(null, avlTree.searchNode(node));
      assertEquals(false, avlTree.search(1).hasLeft());
    }
    @DisplayName("Inserta un nodo en un arbol vacio")
    @Test
    public void testInsertMethod(){
        avlTree.insert(1);
        avlTree.insert(2);
        assertEquals(" | 1 | 2", avlTree.toString());
    }
    @DisplayName("Prueba que en un arbol vacío el top es nulo")
    @Test
    public void testEmptyTree(){
        AvlNode<Integer> node;

        node = new AvlNode<Integer>(null);
        avlTree.insertAvlNode(node);
        avlTree.top = null;

        assertNull(avlTree.searchNode(null));
        assertNull(avlTree.top);
    }

    @DisplayName("Prueba el último caso del switch para pasar por default en insertAvlNode")
    @Test
    public void InsertAvlNodeTest(){
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

        assertEquals(avlTree.searchClosestNode(node), 0);
        assertEquals(3, avlTree.getTop().getHeight());
    }

    @DisplayName("Borra un nodo que solo tenga hijo izqdo")
    @Test
    public void deleteNodeTest(){
        AvlNode<Integer> node;

        node = new AvlNode<>(20);
        avlTree.insertAvlNode(node);

        node = new AvlNode<>(30);
        avlTree.insertAvlNode(node);

        node = new AvlNode<>(25);
        avlTree.insertAvlNode(node);

        node = new AvlNode<>(23);
        avlTree.insertAvlNode(node);

        node = new AvlNode<>(28);
        avlTree.insertAvlNode(node);
      avlTree.delete(28);
      avlTree.delete(18);
      assertEquals(" | 25 | 20 | 23 | 30", avlTree.toString());
    }

    @Test
    @DisplayName("DeleteNode con AVLNode en la rama derecha del arbol")
    public void Given_AVLTreeWithElement3InRightBranch_When_DeleteNode3_Then_AVLNode3IsNotInTree() {
      avlTree.insert(1);
      avlTree.insert(3);
      AvlNode node = new AvlNode(3);

      avlTree.deleteNode(node);

      assertEquals(null, avlTree.searchNode(node));
      assertEquals(false, avlTree.search(1).hasRight());
    }

    @Test
    @DisplayName("DeleteNode del AVLNode padre de una de las hojas en el arbol")
    public void Given_AVLTreeWithElement1InRightBranch_When_DeleteNodeRoot_Then_AVLNode1IsNotInTree() {
      avlTree.insert(1);
      avlTree.insert(3);
      AvlNode node = new AvlNode(1);

      avlTree.deleteNode(node);

      assertEquals(null, avlTree.searchNode(node));
      assertEquals(false, avlTree.search(3).hasParent());
    }

    @Test
    @DisplayName("DeleteNode con AvlNode no en el arbol")
    public void Given_AVLTreeWithNoElementsInside_When_DeleteNode_Then_NothingHappens() {
      AvlNode node = new AvlNode(3);

      assertEquals(null, avlTree.searchNode(node));
    }
  }

  /*
    DeleteLeafNode
        Con AVLNode nulo
        Con AVLNode en el árbol en la raíz
        Con AVLNode en el árbol en la rama izquierda
        Con AVLNode en el árbol en la rama derecha
        Con AVLNode no en el árbol
   */
  @Nested
  @DisplayName("Tests para DeleteLeafNode")
  class TestDeleteLeafNode {
    @Test
    @DisplayName("DeleteLeafNode con AVLNode null")
    public void Given_AVLTree_When_DeleteLeafNodeNull_Then_NothingHappens() {
      avlTree.insert(3);
      Assertions.assertThrows(Exception.class, () -> avlTree.deleteLeafNode(null));
      assertEquals(3, (int) avlTree.search(3).getItem());
    }

    @Test
    @DisplayName("DeleteLeafNode con AVLNode hoja en la raíz del arbol")
    public void Given_AVLTreeWithElement1InRoot_When_DeleteLeafNode1_Then_AVLNode1IsNotInTree() {
      avlTree.insert(1);
      AvlNode node = new AvlNode(1);

      avlTree.deleteLeafNode(node);

      assertEquals(null, avlTree.searchNode(node));
    }

    @Test
    @DisplayName("DeleteLeafNode con AVLNode en la rama izquierda del arbol")
    public void Given_AVLTreeWithElement0_When_DeleteLeafNodeLeftChild0_Then_AVLNode0IsNotInTree() {
      avlTree.insert(1);
      avlTree.insert(0);

      avlTree.deleteLeafNode(avlTree.getTop().getLeft());

      assertEquals(null, avlTree.search(0));
      assertEquals(false, avlTree.search(1).hasLeft());
    }

    @Test
    @DisplayName("DeleteLeafNode con AVLNode en la rama derecha del arbol")
    public void Given_AVLTreeWithElement3_When_DeleteLeafNodeRightChild3_Then_AVLNode3IsNotInTree() {
      avlTree.insert(1);
      avlTree.insert(3);

      avlTree.deleteLeafNode(avlTree.search(3));

      assertEquals(null, avlTree.search(3));
      assertEquals(false, avlTree.search(1).hasRight());
    }

    @Test
    @DisplayName("DeleteLeafNode con AvlNode no en el arbol")
    public void Given_AVLTreeWithNoElementsInside_When_DeleteLeafNode_Then_NothingHappens() {
      AvlNode node = new AvlNode(3);

      avlTree.deleteLeafNode(node);

      assertEquals(true, avlTree.avlIsEmpty());
    }
  }

  /*
* DeleteNodeWithALeftChild
      Con AVLNode nulo
      Con AVLNode en el árbol en la raíz
      Con AVLNode no en el árbol
*/
  @Nested
  @DisplayName("Tests para DeleteNodeWithALeftChild")
  class TestDeleteNodeWithALeftChild {
    @Test
    @DisplayName("DeleteNodeWithALeftChild con AVLNode.Left null")
    public void Given_AVLTree_When_DeleteNodeWithALeftChildNull_Then_NothingHappens() {
      avlTree.insert(3);
      Assertions.assertThrows(Exception.class, () -> avlTree.deleteNodeWithALeftChild(null));
    }

    @Test
    @DisplayName("DeleteNodeWithALeftChild con AVLNode.Left en el arbol")
    public void Given_AVLTreeWithElement1InRoot_When_DeleteNodeWithALeftChild1_Then_AVLNode1IsNotInTree() {
      avlTree.insert(1);
      avlTree.insert(0);
      AvlNode node = new AvlNode(1);

      avlTree.deleteNodeWithALeftChild(avlTree.getTop());

      assertEquals(null, avlTree.searchNode(node));
      assertEquals(0, (int) avlTree.top.getItem());
    }

    @Test
    @DisplayName("DeleteNodeWithALeftChild con AVLNode.Left no en el arbol")
    public void Given_AVLTreeWithElement0_When_DeleteNodeWithALeftChild0_Then_AVLNode0IsNotInTree() {
    avlTree.insert(1);
    AvlNode node = avlTree.search(1);
    node.setLeft(new AvlNode(0));

    avlTree.deleteLeafNode(node);

    assertEquals(null, avlTree.searchNode(node));
    assertEquals(null, avlTree.top);
  }
  }

  /*
DeleteNodeWithARightChild
      Con AVLNode nulo
      Con AVLNode en el árbol en la raíz
      Con AVLNode en el árbol en la rama izquierda
      Con AVLNode en el árbol en la rama derecha
      Con AVLNode no en el árbol
 */
  @Nested
  @DisplayName("Tests para DeleteNodeWithARightChild")
  class TestDeleteNodeWithARightChild {
    @Test
    @DisplayName("DeleteNodeWithARightChild con AVLNode.Left null")
    public void Given_AVLTree_When_DeleteNodeWithARightChildNull_Then_NothingHappens() {
      avlTree.insert(3);
      Assertions.assertThrows(Exception.class, () -> avlTree.deleteNodeWithARightChild(null));
    }

    @Test
    @DisplayName("DeleteNodeWithARightChild con AVLNode.Left en el arbol")
    public void Given_AVLTreeWithElement1InRoot_When_DeleteNodeWithARightChild1_Then_AVLNode1IsNotInTree() {
      avlTree.insert(1);
      avlTree.insert(2);
      AvlNode node = new AvlNode(1);

      avlTree.deleteNodeWithARightChild(avlTree.getTop());

      assertEquals(null, avlTree.searchNode(node));
      assertEquals(2, (int) avlTree.top.getItem());
    }

    @Test
    @DisplayName("DeleteNodeWithARightChild con AVLNode.Left no en el arbol")
    public void Given_AVLTreeWithElement0_When_DeleteNodeWithARightChild0_Then_AVLNode0IsNotInTree() {
      avlTree.insert(1);
      AvlNode node = avlTree.search(1);
      node.setRight(new AvlNode(2));

      avlTree.deleteLeafNode(node);

      assertEquals(null, avlTree.searchNode(node));
      assertEquals(null, avlTree.top);
    }
  }

  /*
SearchClosestNode
      Con AVLNode nulo
      Con AVLNode en el árbol en la raíz y nuevo nodo mas pequeño
      Con AVLNode en el árbol en la raíz y nuevo nodo mas grande
      Con AVLNode en el árbol en la rama derecha y nuevo nodo mas pequeño
      Con AVLNode en el árbol en la rama derecha y nuevo nodo mas grande
      Con AVLNode en el árbol en la rama izquierda y nuevo nodo mas pequeño
      Con AVLNode en el árbol en la rama izquierda y nuevo nodo mas grande
 */
  @Nested
  @DisplayName("Tests para SearchClosestNode")
  class TestSearchClosestNode {
    @Test
    @DisplayName("SearchClosestNode con AVLNode nulo")
    public void Given_AVLTree_When_SearchClosestNodeNull_Then_Exception() {
      assertEquals(0, avlTree.searchClosestNode(null));
    }

    @Test
    @DisplayName("SearchClosestNode con AVLNode en la raiz del arbol mas grande que nuevo nodo")
    public void Given_AVLTreeWithElement1InRoot_When_SearchClosestNode0_Then_Negative1() {
      avlTree.insert(1);
      assertEquals(-1, avlTree.searchClosestNode(new AvlNode<>(0)));
    }

    @Test
    @DisplayName("SearchClosestNode con AVLNode en la raiz del arbol mas pequeño que nuevo nodo")
    public void Given_AVLTreeWithElement0InRoot_When_SearchClosestNode1_Then_1() {
      avlTree.insert(0);
      assertEquals(1, avlTree.searchClosestNode(new AvlNode<>(1)));
    }

    @Test
    @DisplayName("SearchClosestNode con AVLNode mas cercano en la rama derecha del arbol y mas pequeño que nuevo nodo")
    public void Given_AVLTreeWithElement1InRightBranch_When_SearchClosestNode2_Then_1() {
      avlTree.insert(0);
      avlTree.insert(1);
      assertEquals(1, avlTree.searchClosestNode(new AvlNode<>(2)));
    }

    @Test
    @DisplayName("SearchClosestNode con AVLNode mas cercano en la rama derecha del arbol y mas grande que nuevo nodo")
    public void Given_AVLTreeWithElement1InRightBranch_When_SearchClosestNode2_Then_Neg1() {
      avlTree.insert(0);
      avlTree.insert(2);
      assertEquals(-1, avlTree.searchClosestNode(new AvlNode<>(1)));
    }

    @Test
    @DisplayName("SearchClosestNode con AVLNode mas cercano en la rama izquierda del arbol y mas pequeño que nuevo nodo")
    public void Given_AVLTreeWithElement0InLeftBranch_When_SearchClosestNode1_Then_1() {
      avlTree.insert(2);
      avlTree.insert(0);
      assertEquals(1, avlTree.searchClosestNode(new AvlNode<>(1)));
    }

    @Test
    @DisplayName("SearchClosestNode con AVLNode mas cercano en la rama izquierda del arbol y mas pequeño que nuevo nodo")
    public void Given_AVLTreeWithElement1InLeftBranch_When_SearchClosestNode0_Then_Neg1() {
      avlTree.insert(2);
      avlTree.insert(1);
      assertEquals(-1, avlTree.searchClosestNode(new AvlNode<>(0)));
    }
  }

  /*
  InsertAVLNode
    AVLNode nulo
    AVLNode no nulo
 */
  @Nested
  @DisplayName("Tests para InsertAVLNode")
  class TestInsertAVLNode {
    @Test
    @DisplayName("InsertAVLNode null node")
    public void Given_AVLTree_When_InsertAVLNodeNull_Then_NothingHappens() {
      avlTree.insertAvlNode(null);
      assertEquals(null, avlTree.getTop());
    }

    @Test
    @DisplayName("InsertAVLNode not null node")
    public void Given_AVLTree_When_InsertAVLNode_Then_Inserted() {
      avlTree.insertAvlNode(new AvlNode<>(3));
      assertEquals(3, (int) avlTree.getTop().getItem());
    }

  }

  /*
    InsertTop
      AVLNode nulo
      AVLNode no nulo
   */
  @Nested
  @DisplayName("Tests para InsertTop")
  class TestInsertTop {
    @Test
    @DisplayName("InsertTop null node")
    public void Given_AVLTree_When_InsertTopNull_Then_NothingHappens() {
      avlTree.insertTop(null);
      assertEquals(null, avlTree.getTop());
    }

    @Test
    @DisplayName("InsertTop AVLNode")
    public void Given_AVLTree_When_InsertTop_Then_NothingHappens() {
      avlTree.insertTop(new AvlNode<>(3));
      assertEquals(3, (int) avlTree.getTop().getItem());
    }
  }

  /*
    SetTop
      AVLNode null
      AVLNode not null
   */
  @Nested
  @DisplayName("Tests para SetTop")
  class TestSetTop {
    @Test
    @DisplayName("SetTop null AVLNode")
    public void Given_AVLTree_When_SetTopNull_Then_NothingHappens() {
      avlTree.insertTop(new AvlNode<>(1));
      Assertions.assertThrows(Exception.class, () -> avlTree.setTop(null));
    }

    @Test
    @DisplayName("SetTop AVLNode")
    public void Given_AVLTree_When_SetTop_Then_NothingHappens() {
      avlTree.insertTop(new AvlNode<>(1));
      avlTree.setTop(new AvlNode<>(3));
      assertEquals(3, (int) avlTree.getTop().getItem());
    }
  }

  /*
  Height
    AVLTree pero AVLNode es null
    AVLTree with nodes
 */
  @Nested
  @DisplayName("Tests para Height")
  class TestHeight {
    @Test
    @DisplayName("Height null AVLTree")
    public void Given_AVLTreeNull_When_Height_Then_0() {
      assertEquals(-1, avlTree.height(null));
    }

    @Test
    @DisplayName("Height AVLTree con un piso")
    public void Given_AVLTreeWithHeight1_When_Height1_Then_0() {
      avlTree.insert(1);
      assertEquals(0, avlTree.height(avlTree.getTop()));
    }

    @Test
    @DisplayName("Height AVLTree con un piso")
    public void Given_AVLTreeWithHeight2_When_Height_Then_1() {
      avlTree.insert(1);
      avlTree.insert(2);
      assertEquals(1, avlTree.height(avlTree.getTop()));
    }
  }

  /*
  InOrder
    AVLNode not null
 */
  @Nested
  @DisplayName("Tests para InOrder")
  class TestInOrder {
    @Test
    @DisplayName("Inorder null")
    public void Given_AVLTree_When_InOrderNull_Then_Blank() {
      assertEquals(" | 2", ReflectionTestUtils.invokeMethod(avlTree, "inOrder", new AvlNode<>(2)).toString());
    }
  }

  /*
    AVLIsEmpty
      AVLTree vacio
      AVLTree no vacio
   */
  @Nested
  @DisplayName("Tests para IsEmpty")
  class TestIsEmpty {
    @Test
    @DisplayName("AVLIsEmpty con arbol vacio")
    public void Given_AVLTreeEmpty_When_IsEmpty_Then_True() {
      assertEquals(true, avlTree.avlIsEmpty());
    }

    @Test
    @DisplayName("AVLisEmpty con arbol no vacio")
    public void Given_AVLTreeNotEmpty_When_IsEmpty_Then_False() {
      avlTree.insert(2);
      assertEquals(false, avlTree.avlIsEmpty());
    }
  }

  /*
    GetTop
      AVLTree vacio
      AVLTree no vacio
   */
  @Nested
  @DisplayName("Tests para GetTop")
  class TestGetTop {

    @Test
    @DisplayName("GetTop con arbol vacio")
    public void Given_AVLTreeEmpty_When_GetTop_Then_Null() {
      assertEquals(null, avlTree.getTop());
    }

    @Test
    @DisplayName("GetTop con arbol no vacio")
    public void Given_AVLTreeNotEmpty_When_GetTop_Then_AVLNode() {
      avlTree.insert(2);
      assertEquals(2, (int) avlTree.getTop().getItem());
    }
  }

  /*
  toString
    AVLTree vacio
    AVLTree no vacio
   */
  @Nested
  @DisplayName("Tests para ToString")
  class TestToString {
    @Test
    @DisplayName("ToString con arbol vacio")
    public void Given_AVLTreeEmpty_When_ToString_Then_Blank() {
      assertEquals("", avlTree.toString());
    }

    @Test
    @DisplayName("ToString con arbol no vacio")
    public void Given_AVLTreeNotEmpty_When_ToString_Then_AVLNodeItem() {
      avlTree.insert(2);
      assertEquals(" | 2", avlTree.toString());
    }
  }

  @Nested
  @DisplayName("Tests for search method with items")
  public class TestSearch2 {
    @Test
    @DisplayName("Search with null item")
    void testSearchWithNullItem() {
      assertNull(avlTree.search(null));
    }

    @Test
    @DisplayName("Search with item in root")
    public void testSearchWithItemInRoot() {
      avlTree.insert(5);

      AvlNode<Integer> result = avlTree.search(5);

      assertEquals(5, (int)result.getItem());
    }

    @Test
    @DisplayName("Search with item in left branch")
    public void testSearchWithItemInLeftBranch() {
      avlTree.insert(5);
      avlTree.insert(3);

      AvlNode<Integer> result = avlTree.search(3);

      assertEquals(3, (int)result.getItem());
    }

    @Test
    @DisplayName("Search with item in right branch")
    public void testSearchWithItemInRightBranch() {
      avlTree.insert(5);
      avlTree.insert(7);

      AvlNode<Integer> result = avlTree.search(7);

      assertEquals(7, (int)result.getItem());
    }

    @Test
    @DisplayName("Search with item not in tree")
   public void testSearchWithItemNotInTree() {
      avlTree.insert(5);
      avlTree.insert(3);
      avlTree.insert(7);

      AvlNode<Integer> result = avlTree.search(8);

      assertNull(result);
    }
  }

  @Nested
  @DisplayName("Tests for delete method with items")
  public class TestDelete2 {
    @Test
    @DisplayName("Delete with item in root")
    public void testDeleteWithItemInRoot() {
      avlTree.insert(10);
      avlTree.delete(10);
      assertNull(avlTree.search(10));
    }

    @Test
    @DisplayName("Delete with item in left branch")
    public void testDeleteWithItemInLeftBranch() {
      avlTree.insert(10);
      avlTree.insert(5);
      avlTree.delete(5);
      assertNull(avlTree.search(5));
    }

    @Test
    @DisplayName("Delete with item in right branch")
    public void testDeleteWithItemInRightBranch() {
      avlTree.insert(10);
      avlTree.insert(15);
      avlTree.delete(15);
      assertNull(avlTree.search(15));
    }

    @Test
    @DisplayName("Delete with item not in tree")
    public void testDeleteWithItemNotInTree() {
      avlTree.insert(10);
      avlTree.delete(20);
      assertNotNull(avlTree.search(10));
    }
  }

  @Test
  @DisplayName("Insert with valid item")
  public void testInsertWithValidItem() {
    avlTree.insert(5);
    assertEquals(5, (int)avlTree.top.getItem());
    avlTree.insert(3);
    assertEquals(5, (int)avlTree.top.getItem());
    assertEquals(3, (int)avlTree.top.getLeft().getItem());
    avlTree.insert(7);
    assertEquals(5, (int)avlTree.top.getItem());
    assertEquals(3, (int)avlTree.top.getLeft().getItem());
    assertEquals(7, (int)avlTree.top.getRight().getItem());
  }

  @Nested
  @DisplayName("Tests findSuccessor")
  class TestsFindSuccessor{
    @Test
    @DisplayName("findSuccessor con AVLNode null")
    public void FindSuccessorNull_Throws_NullPointerException(){
      assertThrows(NullPointerException.class, () -> avlTree.findSuccessor(null));
    }

    @Test
    @DisplayName("findSuccessor con AVLNode valido en el arbol")
    public void FindSuccessor_Returns_AVLNode_Successor(){
      AvlNode<Integer> node1;

      node1 = new AvlNode<>(5);
      avlTree.insertAvlNode(node1);

      AvlNode<Integer> node2 = new AvlNode<>(3);
      avlTree.insertAvlNode(node2);

      AvlNode<Integer> node3 = new AvlNode<>(7);
      avlTree.insertAvlNode(node3);

      assertEquals(avlTree.search(7), avlTree.findSuccessor(node1));
    }

    @Test
    @DisplayName("finSuccessor con AVLNode no incluido en el arbol")
    public void FindSuccessor_WithoutAVLNodeInTree_ReturnsNull(){
      AvlNode<Integer> node1;
      node1 = new AvlNode<>(5);

      AvlNode<Integer> node2 = new AvlNode<>(3);
      avlTree.insertAvlNode(node2);

      AvlNode<Integer> node3 = new AvlNode<>(7);
      avlTree.insertAvlNode(node3);

      assertNull(avlTree.findSuccessor(node1));

      avlTree.delete(7);
      assertNull(avlTree.findSuccessor(node1));
    }
  }

  /*
      InsertNodeLeft
          Con AVLNode nulo
          Con AVLNode en el arbol
          Con AVLNode no en el arbol
   */
  @Nested
  @DisplayName("Tests insertNodeLeft")
  class TestInsertNodeLeft{
    @Test
    @DisplayName("insertNodeLeft con AVLNode null")
    public void InsertNodeLeftNull_Throws_NullPointerException(){
      assertThrows(NullPointerException.class, () -> avlTree.insertNodeLeft(null));
    }

    @Test
    @DisplayName("insertNodeLeft con AVLNode en el arbol")
    public void InsertNodeLeft_InsertNode_In_LeftBranch(){
      AvlNode<Integer> node;
      node = new AvlNode<>(10);
      avlTree.insertAvlNode(node);

      node = new AvlNode<>(5);
      avlTree.insertAvlNode(node);
      avlTree.insertNodeLeft(node);

      assertEquals(avlTree.search(5), avlTree.getTop().getLeft());
    }

    @Test
    @DisplayName("insertNodeLeft con AVLNode en el arbol")
    public void InsertNodeLeft_Without_AVLNode_InTree(){
      AvlNode<Integer> node;
      node = new AvlNode<>(5);

      assertThrows(NullPointerException.class, () -> avlTree.insertNodeLeft(node));
    }
  }

  /*
      InsertNodeRight
          Con AVLNode nulo
          Con AVLNode en el arbol
          Con AVLNode no en el arbol
   */
  @Nested
  @DisplayName("Tests insertNodeRight")
  class TestInsertNodeRight{
    @Test
    @DisplayName("InsertNodeRight con AVLNode null")
    public void InsertNodeRightNull_Throws_NullPointerException(){
      AvlNode<Integer> node;
      node = new AvlNode<>(10);
      avlTree.insertAvlNode(node);

      AvlNode<Integer> node2 = null;
      assertThrows(NullPointerException.class, () -> avlTree.insertNodeRight(node2));
    }
    @Test
    @DisplayName("InsertNodeRight con AVLNode en el arbol")
    public void InsertNodeRight_With_AVLNode_InTheTree(){
      AvlNode<Integer> node;
      node = new AvlNode<>(5);
      avlTree.insertAvlNode(node);

      node = new AvlNode<>(10);
      avlTree.insertAvlNode(node);
      avlTree.insertNodeRight(node);

      assertEquals(10, (int)avlTree.getTop().getRight().getItem());
    }

    @Test
    @DisplayName("InsertNodeRight sin AVLNode en el arbol")
    public void InsertNodeRight_Without_AVLNode_InTheTree(){
      AvlNode<Integer> node;
      node = new AvlNode<>(5);

      assertThrows(NullPointerException.class, () -> avlTree.insertNodeRight(node));
    }


  }

  /*
      ComparateNodes
          Con AVLNode nulo
   */
  @Nested
  @DisplayName("Tests para compareNodes")
  class TestCompareNodes{
    @Test
    @DisplayName("ComparateNodes con parametros null")
    public void CompareNodes_Throws_NullPointerException(){
      AvlNode<Integer> node1 = null;
      AvlNode<Integer> node2 = new AvlNode<>(1);
      AvlNode<Integer> node3 = null;

      assertThrows(NullPointerException.class, () -> avlTree.compareNodes(node1, node2));
      assertThrows(NullPointerException.class, () -> avlTree.compareNodes(node2, node1));
      assertThrows(NullPointerException.class, () -> avlTree.compareNodes(node1, node3));
    }
  }

  /*
      Rebalance
          Con AVLNode nulo
          Con AVLNode en el arbol
          Con AVLNode no en el arbol
   */
  @Nested
  @DisplayName("Tests para rebalance")
  class TestRebalance{
    @Test
    @DisplayName("Rebalance con AVLNode null")
    public void RebalanceNull_Throws_NullPointerException(){
      AvlNode<Integer> node = null;

      assertThrows(NullPointerException.class, () -> avlTree.rebalance(node));
    }

    @Test
    @DisplayName("Rebalance con AVLNode en el arbol")
    public void Rebalance_With_AVLNode_InTree(){
      AvlNode<Integer> node;
      node = new AvlNode<>(5);
      avlTree.insertAvlNode(node);

      node = new AvlNode<>(3);
      avlTree.insertAvlNode(node);

      node = new AvlNode<>(7);
      avlTree.insertAvlNode(node);

      assertEquals(0, avlTree.getBalance(avlTree.search(5)));

      node = new AvlNode<>(1);
      avlTree.insertAvlNode(node);

      node = new AvlNode<>(0);
      avlTree.insertAvlNode(node);

      assertEquals(-1,avlTree.getBalance(avlTree.search(5)));
    }

    @Test
    @DisplayName("Rebalance sin AVLNode en el arbol")
    public void Rebalance_Without_AVLNode_InTree(){
      AvlNode<Integer> node;
      node = new AvlNode<>(5);

      node = new AvlNode<>(3);
      avlTree.insertAvlNode(node);

      node = new AvlNode<>(7);
      avlTree.insertAvlNode(node);

      assertThrows(NullPointerException.class, () -> avlTree.rebalance(avlTree.search(5)));
    }
  }

  /*
      LeftRotation
          Con AVLNode nulo
          Con AVLNode no en el arbol
   */
  @Nested
  @DisplayName("Tests para leftRotation")
  class TestLeftRotation{
    @Test
    @DisplayName("LeftRotation con AVLNode null")
    public void LeftRotationNull_Throws_NullPointerException(){
      AvlNode<Integer> node = null;

      assertThrows(NullPointerException.class, () -> avlTree.leftRotation(node));
    }

    @Test
    @DisplayName("LeftRotation sin AVLNode en el arbol")
    public void LeftRotation_Without_AVLNode_InTheTree(){
      AvlNode<Integer> node;
      node = new AvlNode<>(5);

      node = new AvlNode<>(7);
      avlTree.insertAvlNode(node);

      node = new AvlNode<>(9);
      avlTree.insertAvlNode(node);

      assertThrows(NullPointerException.class, () -> avlTree.leftRotation(avlTree.search(5)));
    }
  }

  /*
      RightRotation
          Con AVLNode nulo
          Con AVLNode no en el arbol
   */
  @Nested
  @DisplayName("Tests para rightRotation")
  class TestRightRotation{
    @Test
    @DisplayName("RightRotation con AVLNode null")
    public void RightRotationNull_Throws_NullPointerException(){
      AvlNode<Integer> node = null;

      assertThrows(NullPointerException.class, () -> avlTree.rightRotation(node));
    }

    @Test
    @DisplayName("RightRotation sin AVLNode en el arbol")
    public void RightRotation_Without_AVLNode_InTheTree(){
      AvlNode<Integer> node;
      node = new AvlNode<>(5);

      node = new AvlNode<>(7);
      avlTree.insertAvlNode(node);

      node = new AvlNode<>(9);
      avlTree.insertAvlNode(node);

      assertThrows(NullPointerException.class, () -> avlTree.rightRotation(avlTree.search(5)));
    }
  }

  /*
      DoubleLeftRotation
          Con AVLNode nulo
          Con AVLNode en el arbol
          Con AVLNode no en el arbol
   */
  @Nested
  @DisplayName("Tests para doubleLeftRotation")
  class TestDoubleLeftRotation{
    @Test
    @DisplayName("DoubleLeftRotation con AVLNode null")
    public void DoubleLeftRotationNull_Throws_NullPointerException(){
      AvlNode<Integer> node = null;

      assertThrows(NullPointerException.class, () -> avlTree.doubleLeftRotation(node));
    }

    @Test
    @DisplayName("DoubleLeftRotation con AVLNode en el arbol")
    public void DoubleLeftRotation_With_AVLNode_InTheTree(){
      AvlNode<Integer> node;

      node = new AvlNode<>(7);
      avlTree.insertAvlNode(node);

      node = new AvlNode<>(10);
      avlTree.insertAvlNode(node);

      node = new AvlNode<>(8);
      avlTree.insertAvlNode(node);

      assertEquals(avlTree.search(8), avlTree.getTop());
    }

    @Test
    @DisplayName("DoubleLeftRotation sin AVLNode en el arbol")
    public void DoubleLeftRotation_Without_AVLNode_InTheTree(){
      AvlNode<Integer> node;

      node = new AvlNode<>(7);

      assertThrows(NullPointerException.class, () -> avlTree.doubleLeftRotation(avlTree.search(7)));
    }
  }

  /*
      DoubleRightRotation
          Con AVLNode nulo
          Con AVLNode en el arbol
          Con AVLNode no en el arbol
   */
  @Nested
  @DisplayName("Tests para doubleRightRotation")
  class TestDoubleRightRotation{
    @Test
    @DisplayName("DoubleRightRotation con AVLNode null")
    public void DoubleRightRotationNull_Throws_NullPointerException(){
      AvlNode<Integer> node = null;

      assertThrows(NullPointerException.class, () -> avlTree.doubleRightRotation(node));
    }

    @Test
    @DisplayName("DoubleRightRotation con AVLNode en el arbol")
    public void DoubleRightRotation_With_AVLNode_InTheTree(){
      AvlNode<Integer> node;

      node = new AvlNode<>(7);
      avlTree.insertAvlNode(node);

      node = new AvlNode<>(3);
      avlTree.insertAvlNode(node);

      node = new AvlNode<>(5);
      avlTree.insertAvlNode(node);

      assertEquals(avlTree.search(5), avlTree.getTop());
    }

    @Test
    @DisplayName("DoubleRightRotation sin AVLNode en el arbol")
    public void DoubleRightRotation_Without_AVLNode_InTheTree(){
      AvlNode<Integer> node;
      node = new AvlNode<>(7);

      assertThrows(NullPointerException.class, () -> avlTree.doubleRightRotation(avlTree.search(7)));
    }
  }

  /*
      GetBalance
          Con AVLNode nulo
          Con AVLNode en el arbol
          Con AVLNode no en el arbol
   */
  @Nested
  @DisplayName("Tests para getBalance")
  class TestGetBalance{
    @Test
    @DisplayName("GetBalance con AVLNode null")
    public void GetBalanceNull_Throws_NullPointerException(){
      AvlNode<Integer> node = null;

      assertThrows(NullPointerException.class, () -> avlTree.getBalance(node));
    }

    @Test
    @DisplayName("GetBalance sin AVLNode en el arbol")
    public void GetBalance_Without_AVLNode_InTheTree(){
      AvlNode<Integer> node;
      node = new AvlNode<>(5);

      assertEquals(0, avlTree.getBalance(node));
    }

    @Test
    @DisplayName("GetBalance con AVLNode en el arbol")
    public void GetBalance_With_AVLNode_InTheTree(){
      AvlNode<Integer> node;
      node = new AvlNode<>(5);
      avlTree.insertAvlNode(node);

      node = new AvlNode<>(6);
      avlTree.insertAvlNode(node);

      assertEquals(1, avlTree.getBalance(avlTree.search(5)));

      avlTree.delete(6);

      node = new AvlNode<>(4);
      avlTree.insertAvlNode(node);

      assertEquals(-1, avlTree.getBalance(avlTree.search(5)));
    }
  }
}

