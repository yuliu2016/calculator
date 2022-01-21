package org.fugalang.grammar.peg.visitor;

import org.fugalang.grammar.peg.wrapper.*;

@SuppressWarnings("unused")
public interface MetaVisitor<T> {

    /**
     * grammar:
     * *   | [NEWLINE] element+
     */
    default T visitGrammar(Grammar grammar) {
        return null;
    }

    /**
     * element:
     * *   | directive
     * *   | rule
     */
    default T visitElement(Element element) {
        return null;
    }

    /**
     * directive:
     * *   | '.' NAME '(' [arguments] ')' NEWLINE
     */
    default T visitDirective(Directive directive) {
        return null;
    }

    /**
     * arguments:
     * *   | ','.argument+ [',']
     */
    default T visitArguments(Arguments arguments) {
        return null;
    }

    /**
     * argument:
     * *   | STRING
     */
    default T visitArgument(Argument argument) {
        return null;
    }

    /**
     * rule:
     * *   | NAME [return_type] [rule_args] rule_suite
     */
    default T visitRule(Rule rule) {
        return null;
    }

    /**
     * return_type:
     * *   | '[' NAME ['*'] ']'
     */
    default T visitReturnType(ReturnType returnType) {
        return null;
    }

    /**
     * rule_args:
     * *   | '(' ','.rule_arg+ ')'
     */
    default T visitRuleArgs(RuleArgs ruleArgs) {
        return null;
    }

    /**
     * rule_arg:
     * *   | NAME ['=' NAME]
     */
    default T visitRuleArg(RuleArg ruleArg) {
        return null;
    }

    /**
     * rule_suite:
     * *   | ':' NEWLINE '|' alt_list NEWLINE
     */
    default T visitRuleSuite(RuleSuite ruleSuite) {
        return null;
    }

    /**
     * alt_list:
     * *   | sequence alternative*
     */
    default T visitAltList(AltList altList) {
        return null;
    }

    /**
     * alternative:
     * *   | [NEWLINE] '|' sequence
     */
    default T visitAlternative(Alternative alternative) {
        return null;
    }

    /**
     * sequence:
     * *   | primary+ [inline_hint] [result_expr]
     */
    default T visitSequence(Sequence sequence) {
        return null;
    }

    /**
     * inline_hint:
     * *   | '>>' [return_type]
     */
    default T visitInlineHint(InlineHint inlineHint) {
        return null;
    }

    /**
     * result_expr:
     * *   | [NEWLINE] '{' expression '}'
     */
    default T visitResultExpr(ResultExpr resultExpr) {
        return null;
    }

    /**
     * expr_name:
     * *   | '.'.NAME+
     */
    default T visitExprName(ExprName exprName) {
        return null;
    }

    /**
     * expr_arg:
     * *   | '%' NAME
     * *   | NUMBER
     * *   | expr_call
     * *   | expr_name
     */
    default T visitExprArg(ExprArg exprArg) {
        return null;
    }

    /**
     * expr_call:
     * *   | expr_name '(' [','.expr_arg+ [',']] ')'
     */
    default T visitExprCall(ExprCall exprCall) {
        return null;
    }

    /**
     * expression:
     * *   | expr_call
     * *   | NAME
     * *   | STRING
     */
    default T visitExpression(Expression expression) {
        return null;
    }

    /**
     * group:
     * *   | '(' alt_list ')'
     */
    default T visitGroup(Group group) {
        return null;
    }

    /**
     * optional:
     * *   | '[' alt_list ']'
     */
    default T visitOptional(Optional optional) {
        return null;
    }

    /**
     * small_optional:
     * *   | (NAME | STRING) '?'
     */
    default T visitSmallOptional(SmallOptional smallOptional) {
        return null;
    }

    /**
     * delimited:
     * *   | STRING '.' item '+'
     */
    default T visitDelimited(Delimited delimited) {
        return null;
    }

    /**
     * custom_match:
     * *   | '@' expression
     */
    default T visitCustomMatch(CustomMatch customMatch) {
        return null;
    }

    /**
     * primary:
     * *   | delimited
     * *   | '&' item
     * *   | '!' item
     * *   | item '*'
     * *   | item '+'
     * *   | item
     */
    default T visitPrimary(Primary primary) {
        return null;
    }

    /**
     * item:
     * *   | group
     * *   | optional
     * *   | custom_match
     * *   | small_optional
     * *   | NAME
     * *   | STRING
     */
    default T visitItem(Item item) {
        return null;
    }
}
