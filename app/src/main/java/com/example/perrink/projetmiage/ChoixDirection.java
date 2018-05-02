package com.example.perrink.projetmiage;

/**
 * Created by perrink on 25/04/18.
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChoixDirection {


        @SerializedName("0")
        @Expose
        private Direction _0;
        @SerializedName("1")
        @Expose
        private Direction _1;

        public Direction get0() {
            return _0;
        }

        public void set0(Direction _0) {
            this._0 = _0;
        }

        public Direction get1() {
            return _1;
        }

        public void set1(Direction _1) {
            this._1 = _1;
        }
}
