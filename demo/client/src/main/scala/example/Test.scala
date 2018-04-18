package example



class Test {
  var i = 0

  def test1(): Unit = {
    i = i + 1
    this.test2
    i = i + 1
  }

  def test2(): Unit = {
    i = i + 1
    this.test3
    i = i + 1
  }

  def test3(): Unit = {
    i = i + 1
    this.test4
    i = i + 1
  }

  def test4(): Unit = {
    i = i + 1
    this.test5
    i = i + 1
  }

  def test5(): Unit = {
    i = i + 1
    throw new Exception("Exception in Test5")
    i = i + 1
  }
}