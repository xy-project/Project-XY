package com.projextxy.mech.fmp

import scala.collection.mutable.ArrayBuffer

trait TXyRedstonePart extends TMultiPart {
  def removeChild(part: TXyRedstonePart) = children -= part

  def parents: ArrayBuffer[TXyRedstonePart]

  def isParentIn(part: TXyRedstonePart): Boolean = parents.contains(part)

  def removeAllChildren() {
    children.foreach { c =>
      c.removeParent(this)
      c.removeAllChildren()
    }
    children.clear()
  }

  def removeAllParents() {
    parents.foreach { p => removeParent(p) }
    parents.clear()
  }

  def removeParent(part: TXyRedstonePart) {
    if (parents.size == 1 || parents.isEmpty) {
      children.foreach { child =>
        child.removeAllChildren()
        child.removeParent(child)
      }
    }
    parents -= part
  }

  def children: ArrayBuffer[TXyRedstonePart]

  def isChildIn(part: TXyRedstonePart): Boolean = children.contains(part)

  override def onRemoved() {
    removeAllChildren()
    removeAllParents()
  }

  def isPartInChain(part: TXyRedstonePart): Boolean = {
    if (isParentIn(part))
      return true
    parents.foreach { p =>
      if (p.isPartInChain(part))
        return true
    }
    false
  }
}
