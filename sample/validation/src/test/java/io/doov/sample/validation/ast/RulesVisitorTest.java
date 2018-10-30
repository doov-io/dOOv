/*
 * Copyright 2017 Courtanet
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package io.doov.sample.validation.ast;

import static io.doov.core.dsl.impl.DefaultRuleRegistry.REGISTRY_DEFAULT;
import static io.doov.core.dsl.meta.i18n.ResourceBundleProvider.BUNDLE;

import java.io.*;
import java.nio.charset.Charset;
import java.util.Locale;

import javax.script.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import io.doov.core.dsl.meta.ast.*;
import io.doov.sample.validation.SampleRules;

public class RulesVisitorTest {

    @BeforeAll
    public static void init() {
        new SampleRules();
    }

    @Test
    public void print_full_syntax_tree() {
        StringBuilder sb = new StringBuilder();
        REGISTRY_DEFAULT.stream()
                .peek(rule -> sb.append("--------------------------------").append("\n"))
                .forEach(rule -> rule.accept(new AstFullVisitor(sb), 0));
        System.out.println(sb.toString());
    }

    @Test
    public void print_line_syntax_tree() {
        StringBuilder sb = new StringBuilder();
        REGISTRY_DEFAULT.stream()
                .peek(rule -> sb.append("--------------------------------").append("\n"))
                .forEach(rule -> rule.accept(new AstLineVisitor(sb, BUNDLE, Locale.ENGLISH), 0));
        System.out.println(sb.toString());
    }

    @Test
    public void print_text_syntax_tree() {
        StringBuilder sb = new StringBuilder();
        REGISTRY_DEFAULT.stream()
                .peek(rule -> sb.append("--------------------------------").append("\n"))
                .forEach(rule -> rule.accept(new AstTextVisitor(sb, BUNDLE, Locale.ENGLISH), 0));
        System.out.println(sb.toString());
    }

    @Test
    public void print_markdown_syntax_tree() {
        StringBuilder sb = new StringBuilder();
        REGISTRY_DEFAULT.stream()
                .peek(rule -> sb.append("--------------------------------").append("\n"))
                .forEach(rule -> rule.accept(new AstMarkdownVisitor(sb, BUNDLE, Locale.ENGLISH), 0));
        System.out.println(sb.toString());
    }

    @Test
    public void print_html_syntax_tree() {
        ByteArrayOutputStream ops = new ByteArrayOutputStream();
        REGISTRY_DEFAULT.stream()
                .peek(rule -> {
                    try {
                        ops.write("--------------------------------\n".getBytes());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                })
                .forEach(rule -> rule.accept(new AstHtmlVisitor(ops, BUNDLE, Locale.ENGLISH), 0));
        System.out.println(new String(ops.toByteArray(), Charset.forName("UTF-8")));
    }


    @Test
    public void print_javascript_syntax_tree() {
        ByteArrayOutputStream ops = new ByteArrayOutputStream();
        REGISTRY_DEFAULT.stream()
                .peek(rule -> {
                    try {
                        ops.write("--------------------------------\n".getBytes());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                })
                .forEach(rule -> rule.accept(new AstProceduralJSVisitor(ops, BUNDLE, Locale.ENGLISH), 0));

        System.out.println(new String(ops.toByteArray(), Charset.forName("UTF-8")));

        //TODO test the engine evaluation, solve the import moment js problem
//        //WIP - need to creation javascript object to instanciate the account/configuration values to real values

        ScriptEngineManager sem = new ScriptEngineManager();
        ScriptEngine engine = sem.getEngineByName("nashorn");
        REGISTRY_DEFAULT.stream().forEach(rule -> {
            ops.reset();
            try {
                ops.write("var configuration = { max:{email:{size:\"24\"}}, min:{age:\"18\"}};".getBytes("UTF-8"));
                ops.write("var account = {email:\"potato@tomato.com\", creation: {date : \"10-10-2000\"}, country:\"FR\", phone:{number:\"+33555555555\"}};".getBytes("UTF-8"));
                ops.write("var user = {birthdate:\"09-08-1993\",first:{name:\"french\"}, last:{name:\"fries\"} };".getBytes("UTF-8"));

                rule.accept(new AstProceduralJSVisitor(ops, BUNDLE, Locale.ENGLISH), 0);
                String request = new String(ops.toByteArray(), Charset.forName("UTF-8"));
                try {
                    InputStreamReader reader = new InputStreamReader(new FileInputStream(new File("/home/jru/moment.min.js")));
                    engine.eval(reader);
                    System.out.println("    Starting engine checking of : " + rule.readable());
                    Object obj = engine.eval(request);
                    System.out.println(obj.toString());
                    System.out.println("    Ending engine checking.");
                } catch (final ScriptException se) {
                    throw new RuntimeException(se);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });


    }
}
