//  AvlTree.java
//
//  Author:
//       Antonio J. Nebro <antonio@lcc.uma.es>
//       Juan J. Durillo <durillo@lcc.uma.es>
//
//  Copyright (c) 2011 Antonio J. Nebro, Juan J. Durillo
//
//  This program is free software: you can redistribute it and/or modify
//  it under the terms of the GNU Lesser General Public License as published by
//  the Free Software Foundation, either version 3 of the License, or
//  (at your option) any later version.
//
//  This program is distributed in the hope that it will be useful,
//  but WITHOUT ANY WARRANTY; without even the implied warranty of
//  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//  GNU Lesser General Public License for more details.
//
//  You should have received a copy of the GNU Lesser General Public License
//  along with this program.  If not, see <http://www.gnu.org/licenses/>.
//

package avl;

import java.util.Comparator;

/**
 * Created with IntelliJ IDEA. User: Antonio J. Nebro Date: 08/07/13 Time: 15:51 Class implementing
 * Avl trees.
 */
public class AvlTree<T> {

    AvlNode<T> top;
    Comparator comparator;

    /**
     * Constructor
     *
     * @param comparator
     */
    public AvlTree(Comparator comparator) {
        top = null;
        this.comparator = comparator;
    }

    public void insert(T item) {
        AvlNode<T> node = new AvlNode<>(item);
        insertAvlNode(node);
    }

    public void insertAvlNode(AvlNode<T> node) {
        if (avlIsEmpty()) {
            insertTop(node);
        } else {
            int result = searchClosestNode(node);

            switch (result) {
                case -1 -> insertNodeLeft(node);
                case +1 -> insertNodeRight(node);
                default -> {
                }
            }
        }
    }

    public AvlNode<T> search(T item) {
        AvlNode<T> node = new AvlNode<>(item);
        return searchNode(node);
    }

    public AvlNode<T> searchNode(AvlNode<T> targetNode) {
        AvlNode<T> currentNode;
        AvlNode<T> result = null;

        currentNode = top;
        if (top == null) {
            result = null;
        } else {
            boolean searchFinished;
            int comparison;
            searchFinished = false;
            while (!searchFinished) {
                comparison = compareNodes(targetNode, currentNode);
                if (comparison < 0) {
                    if (currentNode.getLeft() != null) {
                        currentNode = currentNode.getLeft();
                    } else {
                        searchFinished = true;
                        result = null;
                    }
                } else if (comparison > 0) {
                    if (currentNode.getRight() != null) {
                        currentNode = currentNode.getRight();
                    } else {
                        searchFinished = true;
                        result = null;
                    }
                } else {
                    searchFinished = true;
                    result = currentNode;
                }
            }
        }
        return result;
    }

    public void delete(T item) {
        deleteNode(new AvlNode<>(item));
    }

    public void deleteNode(AvlNode<T> node) {
        AvlNode<T> nodeFound;

        nodeFound = searchNode(node);
        if (nodeFound != null) {
            if (nodeFound.isLeaf()) {
                deleteLeafNode(nodeFound);
            } else if (nodeFound.hasOnlyALeftChild()) {
                deleteNodeWithALeftChild(nodeFound);
            } else if (nodeFound.hasOnlyARightChild()) {
                deleteNodeWithARightChild(nodeFound);
            } else { // has two children
                AvlNode<T> successor = findSuccessor(nodeFound);
                T tmp = successor.getItem();
                successor.setItem(nodeFound.getItem());
                nodeFound.setItem(tmp);
                if (successor.isLeaf()) {
                    deleteLeafNode(successor);
                }else {
                    deleteNodeWithARightChild(successor);
                }
            }
        }
    }

    public void deleteLeafNode(AvlNode<T> node) {
        if (!node.hasParent()) {
            top = null;
        } else {
            if (node.getParent().getLeft() == node) {
                node.getParent().setLeft(null);
            } else {
                node.getParent().setRight(null);
            }
            node.getParent().updateHeight();
            rebalance(node.getParent());
        }
    }

    public void deleteNodeWithALeftChild(AvlNode<T> node) {
        node.setItem((T) node.getLeft().getItem());
        node.setLeft(null);
        node.updateHeight();
        rebalance(node);
    }

    public void deleteNodeWithARightChild(AvlNode<T> node) {
        node.setItem((T) node.getRight().getItem());
        node.setRight(null);
        node.updateHeight();
        rebalance(node);
    }

    /**
     * Searches for the closest node of the node passed as argument
     *
     * @param node
     * @return -1 if node has to be inserted in the left, +1 if it must be inserted in the right, 0
     * otherwise
     */
    public int searchClosestNode(AvlNode<T> node) {
        AvlNode<T> currentNode;
        int result = 0;

        currentNode = top;
        if (top == null) {
            result = 0;
        } else {
            int comparison;
            boolean notFound = true;
            while (notFound) {
                comparison = compareNodes(node, currentNode);
                if (comparison < 0) {
                    if (currentNode.hasLeft()) {
                        currentNode = currentNode.getLeft();
                    } else {
                        notFound = false;
                        node.setClosestNode(currentNode);
                        result = -1;
                    }
                } else if (comparison > 0) {
                    if (currentNode.hasRight()) {
                        currentNode = currentNode.getRight();
                    } else {
                        notFound = false;
                        node.setClosestNode(currentNode);
                        result = 1;
                    }
                } else {
                    notFound = false;
                    node.setClosestNode(currentNode);
                    result = 0;
                }
            }
        }

        return result;
    }

    public AvlNode<T> findSuccessor(AvlNode<T> node) {
        AvlNode<T> result;

        if (node.hasRight()) {
            AvlNode<T> tmp = node.getRight();
            while (tmp.hasLeft()) {
                tmp = tmp.getLeft();
            }
            result = tmp;
        } else {
            while (node.hasParent() && (node.getParent().getRight() == node)) {
                node = node.getParent();
            }
            result = node.getParent();
        }
        return result;
    }

    /**
     * Insert node in the left of its nearest node
     *
     * @param node REQUIRES: a previous call to searchClosestNode(node)
     */
    public void insertNodeLeft(AvlNode<T> node) {
        node.getClosestNode().setLeft(node);
        node.setParent(node.getClosestNode());
        rebalance(node);
    }

    /**
     * Insert node in the right of its nearest node
     *
     * @param node REQUIRES: a previous call to searchClosestNode(node)
     */
    public void insertNodeRight(AvlNode<T> node) {
        node.getClosestNode().setRight(node);
        node.setParent(node.getClosestNode());
        rebalance(node);
    }

    /**
     * Comparator
     *
     * @param node1
     * @param node2
     * @return The experimentoutput of the comparison according to the comparators
     */
    public int compareNodes(AvlNode<T> node1, AvlNode<T> node2) {
        return comparator.compare(node1.getItem(), node2.getItem());
    }

    public void rebalance(AvlNode<T> node) {
        AvlNode<T> currentNode;
        boolean notFinished;

        currentNode = node;
        notFinished = true;

        while (notFinished) {
            if (getBalance(currentNode) == -2) {
                if (height(currentNode.getLeft().getLeft()) >= height(currentNode.getLeft().getRight())) {
                    leftRotation(currentNode);
                } else {
                    doubleLeftRotation(currentNode);
                }
            }

            if (getBalance(currentNode) == 2) {
                if (height(currentNode.getRight().getRight()) >= height(currentNode.getRight().getLeft())) {
                    rightRotation(currentNode);
                } else {
                    doubleRightRotation(currentNode);
                }
            }

            if (currentNode.hasParent()) {
                currentNode.getParent().updateHeight();
                currentNode = currentNode.getParent();
            } else {
                setTop(currentNode);
                notFinished = false;
            }
        }
    }

    public void leftRotation(AvlNode<T> node) {
        AvlNode<T> leftNode = node.getLeft();

        if (node.hasParent()) {
            leftNode.setParent(node.getParent());
            if (node.getParent().getLeft() == node) {
                node.getParent().setLeft(leftNode);
            } else {
                node.getParent().setRight(leftNode);
            }
        } else {
            setTop(leftNode);
        }

        node.setLeft(node.getLeft().getRight());
        leftNode.setRight(node);
        node.setParent(leftNode);

        node.updateHeight();
        leftNode.updateHeight();
    }

    public void rightRotation(AvlNode<T> node) {
        AvlNode<T> rightNode = node.getRight();

        if (node.hasParent()) {
            rightNode.setParent(node.getParent());
            if (node.getParent().getRight() == node) {
                node.getParent().setRight(rightNode);
            } else {
                node.getParent().setLeft(rightNode);
            }
        } else {
            setTop(rightNode);
        }

        node.setRight(node.getRight().getLeft());
        rightNode.setLeft(node);
        node.setParent(rightNode);

        node.updateHeight();
        rightNode.updateHeight();
    }

    public void doubleLeftRotation(AvlNode<T> node) {
        AvlNode<T> leftNode = node.getLeft();

        rightRotation(leftNode);
        leftRotation(node);
    }

    public void doubleRightRotation(AvlNode<T> node) {
        AvlNode<T> rightNode = node.getRight();

        leftRotation(rightNode);
        rightRotation(node);
    }

    public int getBalance(AvlNode<T> node) {
        int leftHeight;
        int rightHeight;

        if (node.hasLeft()) {
            leftHeight = node.getLeft().getHeight();
        } else {
            leftHeight = -1;
        }
        if (node.hasRight()) {
            rightHeight = node.getRight().getHeight();
        } else {
            rightHeight = -1;
        }

        return rightHeight - leftHeight;
    }

    public boolean avlIsEmpty() {
        return top == null;
    }

    public void insertTop(AvlNode<T> node) {
        top = node;
    }


    public AvlNode<T> getTop() {
        return top;
    }

    public void setTop(AvlNode<T> top) {
        this.top = top;
        this.top.setParent(null);
    }

    public int height(AvlNode<T> node) {
        int result = 0;
        if (node == null) {
            result = -1;
        } else {
            result = node.getHeight();
        }

        return result;
    }

    public String toString() {
        String result;

        result = inOrder(top);

        return result;
    }

    private String inOrder(AvlNode<T> node) {
        String result;
        if (node == null) {
            return "";
        } else {
            result = " | " + node.getItem();
            result += inOrder(node.getLeft());
            result += inOrder(node.getRight());
            return result;
        }
    }
}
