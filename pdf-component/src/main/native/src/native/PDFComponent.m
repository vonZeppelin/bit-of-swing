/*
 * Copyright (c) 2011, Leonid Bogdanov
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
//
//  PDFComponent.m
//
//  Created by Leonid Bogdanov on 04-April-2011
//

#import <JavaNativeFoundation/JavaNativeFoundation.h>
#import "PDFComponent.h"


JNF_CLASS_CACHE(JPDFCOMPONENT, "org/lbogdanov/swing/PDFComponent");

JNF_MEMBER_CACHE(JPDFCOMPONENT_nsViewPtr, JPDFCOMPONENT, "nsViewPtr", "J");


JNIEXPORT jlong JNICALL Java_org_lbogdanov_swing_PDFComponent_initNSViewLong(JNIEnv *env, jobject caller) {
    jlong componentPtr;
JNF_COCOA_ENTER(env)
    NSConditionLock *initLock = [[NSConditionLock alloc] initWithCondition:NO];
    NSMutableArray *holder = [NSMutableArray arrayWithCapacity:1];
    [JNFRunLoop performOnMainThreadWaiting:NO withBlock:^() {
        NSRect frame = NSMakeRect(0, 0, 100, 100);
        [holder addObject:[[[PDFComponent alloc] initWithFrame:frame] autorelease]];
        [initLock lock];
        [initLock unlockWithCondition:YES];
    }];
    [initLock lockWhenCondition:YES];
    [initLock unlock];
    [initLock release];
    componentPtr = (jlong) [[holder objectAtIndex:0] retain];
JNF_COCOA_EXIT(env)
	return componentPtr;
}

JNIEXPORT void JNICALL Java_org_lbogdanov_swing_PDFComponent_setEnablePlugins(JNIEnv *env, jobject caller, jboolean enable) {
JNF_COCOA_ENTER(env)
    PDFComponent *component = [PDFComponent componentOf:caller JNIEnv:env];
    [component.preferences setPlugInsEnabled:((enable) ? YES : NO)];
JNF_COCOA_EXIT(env)
}

JNIEXPORT void JNICALL Java_org_lbogdanov_swing_PDFComponent_setPDF(JNIEnv *env, jobject caller, jstring path) {
JNF_COCOA_ENTER(env)
    PDFComponent *component = [PDFComponent componentOf:caller JNIEnv:env];
    [component setMainFrameURL:JNFNormalizedNSStringForPath(env, path)];
JNF_COCOA_EXIT(env)
}

JNIEXPORT void JNICALL Java_org_lbogdanov_swing_PDFComponent_destroyImpl(JNIEnv *env, jobject caller) {
JNF_COCOA_ENTER(env)
    [[PDFComponent componentOf:caller JNIEnv:env] release];
JNF_COCOA_EXIT(env)
}


@implementation PDFComponent

+ (PDFComponent *) componentOf:(jobject)caller JNIEnv:(JNIEnv *)env {
    return (PDFComponent *) JNFGetLongField(env, caller, JPDFCOMPONENT_nsViewPtr);
}

- (id) initWithFrame:(NSRect)frame {
    if (self = [super initWithFrame:frame frameName:nil groupName:nil]) {
        [self.preferences setPlugInsEnabled:NO];
        [self setShouldCloseWithWindow:YES];
    }
    return self;
}

- (void) awtMessage:(jint)messageID message:(jobject)message env:(JNIEnv *)env {
    switch (messageID) {
        case REDRAW:
            [[self window] display];
            break;
        default:
            [JNFException raise:env as:kIllegalArgumentException reason:"Unknown message"];
            break;
    }
}

@end
