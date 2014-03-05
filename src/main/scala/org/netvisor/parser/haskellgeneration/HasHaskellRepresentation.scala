package parser.haskellgeneration

trait HasHaskellRepresentation {

  /**
   * Builds the Haskell representation of an element.
   *
   * It is the most general form of this functionality since this does not take parameters.
   * @return
   */
  def asHaskell: String = ""
  def asHaskellWithRuleNumber(startRule: Int = 1): (String, Int) = ("", 0)

  /**
   * Used for generating haskell code for a given element.
   *
   * By default it calls the asHaskell method that does not depend on the rule number.
   *
   * TODO: This should include rule name prefix.
   * @param ruleNumber The rule that is going to be generated.
   * @return The haskell code and the number of rules generated. Certain elements may generate more than one.
   */
  def asHaskell(ruleNumber: Int): (String, Int) = (asHaskell, 1)
}
