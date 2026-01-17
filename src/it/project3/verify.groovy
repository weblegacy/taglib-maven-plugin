/*
 * The MIT License
 * Copyright © 2004-2014 Fabrizio Giustina
 * Copyright © 2022-2026 Web-Legacy
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

File buildLog = new File(basedir, 'build.log')
assert buildLog.exists()

assert buildLog.text.contains("[ERROR] Unable to load jsp-classes from project classloader")

def expected = [
    'classes/META-INF/testtaglib.tld',
    'site/tlddoc/test/isAllBlank.fn.html',
    'site/tlddoc/test/lastIndexOf.fn.html',
    'site/tlddoc/test/replaceAll.fn.html',
    'site/tlddoc/test/trim.fn.html',
    'site/taglibvalidation.html',
    'site/tagreference.html'
]

File targetDir = new File(basedir, 'target')
assert targetDir.exists()

def missingFiles = expected.findAll { !new File(targetDir, it).isFile() }

assert missingFiles == []