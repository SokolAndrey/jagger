/*
 * Copyright (c) 2010-2012 Grid Dynamics Consulting Services, Inc, All Rights Reserved
 * http://www.griddynamics.com
 *
 * This library is free software; you can redistribute it and/or modify it under the terms of
 * the Apache License; either
 * version 2.0 of the License, or any later version.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.griddynamics.jagger.coordinator.http;

import java.io.Serializable;

public class PackResponse implements Serializable {
    private Ack ack;
    private Pack pack;
    private long newPollRate;
    private Throwable error;

    public static PackResponse success(Pack pack) {
        PackResponse response = new PackResponse();
        response.pack = pack;
        response.ack = Ack.SUCCESS;
        response.newPollRate = 0;
        return response;
    }

    public static PackResponse fail(Throwable error) {
        PackResponse response = new PackResponse();
        response.ack = Ack.FAIL;
        response.error = error;
        return response;
    }

    public Ack getAck() {
        return ack;
    }

    public Pack getPack() {
        return pack;
    }

    public Throwable getError() {
        return error;
    }

    public long getNewPollRate() {
        return newPollRate;
    }

    public void setNewPollRate(long newPollRate) {
        this.newPollRate = newPollRate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PackResponse that = (PackResponse) o;

        if (ack != that.ack) return false;
        if (error != null ? !error.equals(that.error) : that.error != null) return false;
        if (pack != null ? !pack.equals(that.pack) : that.pack != null) return false;
        return newPollRate == that.newPollRate;
    }

    @Override
    public int hashCode() {
        int result = ack != null ? ack.hashCode() : 0;
        result = 31 * result + (pack != null ? pack.hashCode() : 0);
        result = 31 * result + (error != null ? error.hashCode() : 0);
        result = (int) (31 * result + newPollRate);
        return result;
    }

    @Override
    public String toString() {
        return "PackResponse{" +
                "ack=" + ack +
                ", pack=" + pack +
                ", error=" + error +
                ", newPollRate=" + newPollRate +
                '}';
    }
}
