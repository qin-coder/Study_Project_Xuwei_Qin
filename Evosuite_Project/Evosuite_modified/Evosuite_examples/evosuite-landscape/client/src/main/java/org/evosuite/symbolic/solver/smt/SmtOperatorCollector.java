/*
 * Copyright (C) 2010-2018 Gordon Fraser, Andrea Arcuri and EvoSuite
 * contributors
 *
 * This file is part of EvoSuite.
 *
 * EvoSuite is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published
 * by the Free Software Foundation, either version 3.0 of the License, or
 * (at your option) any later version.
 *
 * EvoSuite is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with EvoSuite. If not, see <http://www.gnu.org/licenses/>.
 */
package org.evosuite.symbolic.solver.smt;

import org.evosuite.symbolic.solver.smt.SmtOperation.Operator;

import java.util.HashSet;
import java.util.Set;

public final class SmtOperatorCollector implements SmtExprVisitor<Void, Void> {

    private final Set<Operator> operators = new HashSet<>();

    @Override
    public Void visit(SmtIntConstant n, Void arg) {
        return null;
    }

    @Override
    public Void visit(SmtRealConstant n, Void arg) {
        return null;
    }

    @Override
    public Void visit(SmtStringConstant n, Void arg) {
        return null;
    }

    @Override
    public Void visit(SmtIntVariable n, Void arg) {
        return null;
    }

    @Override
    public Void visit(SmtRealVariable n, Void arg) {
        return null;
    }

    @Override
    public Void visit(SmtStringVariable n, Void arg) {
        return null;
    }

    @Override
    public Void visit(SmtOperation n, Void arg) {
        operators.add(n.getOperator());
        for (SmtExpr argument : n.getArguments()) {
            argument.accept(this, null);
        }
        return null;
    }

    @Override
    public Void visit(SmtArrayVariable.SmtIntegerArrayVariable n, Void arg) {
        return null;
    }

    @Override
    public Void visit(SmtArrayVariable.SmtRealArrayVariable n, Void arg) {
        return null;
    }

    @Override
    public Void visit(SmtArrayVariable.SmtStringArrayVariable n, Void arg) {
        return null;
    }

    @Override
    public Void visit(SmtArrayVariable.SmtReferenceArrayVariable n, Void arg) {
        return null;
    }

    @Override
    public Void visit(SmtArrayConstant.SmtIntegerArrayConstant n, Void arg) {
        return null;
    }

    @Override
    public Void visit(SmtArrayConstant.SmtRealArrayConstant n, Void arg) {
        return null;
    }

    @Override
    public Void visit(SmtArrayConstant.SmtStringArrayConstant n, Void arg) {
        return null;
    }

    @Override
    public Void visit(SmtArrayConstant.SmtReferenceArrayConstant n, Void arg) {
        return null;
    }

    public Set<Operator> getOperators() {
        return operators;
    }

    @Override
    public Void visit(SmtBooleanConstant n, Void arg) {
        return null;
    }
}
