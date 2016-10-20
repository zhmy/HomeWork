(function(f){if(typeof exports==="object"&&typeof module!=="undefined"){module.exports=f()}else if(typeof define==="function"&&define.amd){define([],f)}else{var g;if(typeof window!=="undefined"){g=window}else if(typeof global!=="undefined"){g=global}else if(typeof self!=="undefined"){g=self}else{g=this}g.GL = f()}})(function(){var define,module,exports;return (function e(t,n,r){function s(o,u){if(!n[o]){if(!t[o]){var a=typeof require=="function"&&require;if(!u&&a)return a(o,!0);if(i)return i(o,!0);var f=new Error("Cannot find module '"+o+"'");throw f.code="MODULE_NOT_FOUND",f}var l=n[o]={exports:{}};t[o][0].call(l.exports,function(e){var n=t[o][1][e];return s(n?n:e)},l,l.exports,e,t,n,r)}return n[o].exports}var i=typeof require=="function"&&require;for(var o=0;o<r.length;o++)s(r[o]);return s})({1:[function(require,module,exports){
"use strict";

var _extends = Object.assign || function (target) { for (var i = 1; i < arguments.length; i++) { var source = arguments[i]; for (var key in source) { if (Object.prototype.hasOwnProperty.call(source, key)) { target[key] = source[key]; } } } return target; };

var _createClass = function () { function defineProperties(target, props) { for (var i = 0; i < props.length; i++) { var descriptor = props[i]; descriptor.enumerable = descriptor.enumerable || false; descriptor.configurable = true; if ("value" in descriptor) descriptor.writable = true; Object.defineProperty(target, descriptor.key, descriptor); } } return function (Constructor, protoProps, staticProps) { if (protoProps) defineProperties(Constructor.prototype, protoProps); if (staticProps) defineProperties(Constructor, staticProps); return Constructor; }; }();

function _objectWithoutProperties(obj, keys) { var target = {}; for (var i in obj) { if (keys.indexOf(i) >= 0) continue; if (!Object.prototype.hasOwnProperty.call(obj, i)) continue; target[i] = obj[i]; } return target; }

function _possibleConstructorReturn(self, call) { if (!self) { throw new ReferenceError("this hasn't been initialised - super() hasn't been called"); } return call && (typeof call === "object" || typeof call === "function") ? call : self; }

function _inherits(subClass, superClass) { if (typeof superClass !== "function" && superClass !== null) { throw new TypeError("Super expression must either be null or a function, not " + typeof superClass); } subClass.prototype = Object.create(superClass && superClass.prototype, { constructor: { value: subClass, enumerable: false, writable: true, configurable: true } }); if (superClass) Object.setPrototypeOf ? Object.setPrototypeOf(subClass, superClass) : subClass.__proto__ = superClass; }

function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError("Cannot call a class as a function"); } }

var isAnimated = require("./isAnimated");

// At the moment, need to dup some things from RN Animated

var Animated = function () {
  function Animated() {
    _classCallCheck(this, Animated);
  }

  _createClass(Animated, [{
    key: "__attach",
    value: function __attach() {}
  }, {
    key: "__detach",
    value: function __detach() {}
  }, {
    key: "__getValue",
    value: function __getValue() {}
  }, {
    key: "__getAnimatedValue",
    value: function __getAnimatedValue() {
      return this.__getValue();
    }
  }, {
    key: "__addChild",
    value: function __addChild() {}
  }, {
    key: "__removeChild",
    value: function __removeChild() {}
  }, {
    key: "__getChildren",
    value: function __getChildren() {
      return [];
    }
  }]);

  return Animated;
}();

var AnimatedWithChildren = function (_Animated) {
  _inherits(AnimatedWithChildren, _Animated);

  function AnimatedWithChildren() {
    _classCallCheck(this, AnimatedWithChildren);

    var _this = _possibleConstructorReturn(this, (AnimatedWithChildren.__proto__ || Object.getPrototypeOf(AnimatedWithChildren)).call(this));

    _this._children = [];
    return _this;
  }

  _createClass(AnimatedWithChildren, [{
    key: "__addChild",
    value: function __addChild(child) {
      if (this._children.length === 0) {
        this.__attach();
      }
      this._children.push(child);
    }
  }, {
    key: "__removeChild",
    value: function __removeChild(child) {
      var index = this._children.indexOf(child);
      if (index === -1) {
        console.warn("Trying to remove a child that doesn't exist");
        return;
      }
      this._children.splice(index, 1);
      if (this._children.length === 0) {
        this.__detach();
      }
    }
  }, {
    key: "__getChildren",
    value: function __getChildren() {
      return this._children;
    }
  }]);

  return AnimatedWithChildren;
}(Animated);

// Animated over the GL Data uniforms object


var AnimatedUniforms = function (_AnimatedWithChildren) {
  _inherits(AnimatedUniforms, _AnimatedWithChildren);

  function AnimatedUniforms(uniforms) {
    _classCallCheck(this, AnimatedUniforms);

    var _this2 = _possibleConstructorReturn(this, (AnimatedUniforms.__proto__ || Object.getPrototypeOf(AnimatedUniforms)).call(this));

    _this2._uniforms = uniforms;
    _this2.__attach();
    return _this2;
  }

  _createClass(AnimatedUniforms, [{
    key: "__getValue",
    value: function __getValue() {
      var u = {};
      var uniforms = this._uniforms;
      for (var key in uniforms) {
        var value = uniforms[key];
        if (value instanceof Array) {
          var arr = [];
          for (var i = 0; i < value.length; i++) {
            var v = value[i];
            arr[i] = isAnimated(v) ? v.__getValue() : v;
          }
          u[key] = arr;
        } else if (isAnimated(value)) {
          u[key] = value.__getValue();
        } else {
          u[key] = value;
        }
      }
      return u;
    }
  }, {
    key: "__attach",
    value: function __attach() {
      var uniforms = this._uniforms;
      for (var key in uniforms) {
        var value = uniforms[key];
        if (value instanceof Array) {
          for (var i = 0; i < value.length; i++) {
            var v = value[i];
            if (isAnimated(v)) {
              v.__addChild(this);
            }
          }
        } else if (isAnimated(value)) {
          value.__addChild(this);
        }
      }
    }
  }, {
    key: "__detach",
    value: function __detach() {
      var uniforms = this._uniforms;
      for (var key in uniforms) {
        var value = uniforms[key];
        if (value instanceof Array) {
          for (var i = 0; i < value.length; i++) {
            var v = value[i];
            if (isAnimated(v)) {
              v.__removeChild(this);
            }
          }
        } else if (isAnimated(value)) {
          value.__removeChild(this);
        }
      }
    }
  }]);

  return AnimatedUniforms;
}(AnimatedWithChildren);

// Animated over a GL Data


var AnimatedData = function (_AnimatedWithChildren2) {
  _inherits(AnimatedData, _AnimatedWithChildren2);

  function AnimatedData(data, callback) {
    _classCallCheck(this, AnimatedData);

    var _this3 = _possibleConstructorReturn(this, (AnimatedData.__proto__ || Object.getPrototypeOf(AnimatedData)).call(this));

    _this3._data = _extends({}, data, {
      contextChildren: data.contextChildren.map(function (d) {
        return new AnimatedData(d);
      }),
      children: data.children.map(function (d) {
        return new AnimatedData(d);
      }),
      uniforms: new AnimatedUniforms(data.uniforms)
    });
    if (callback) _this3.update = callback;
    _this3.__attach();
    return _this3;
  }

  _createClass(AnimatedData, [{
    key: "__getValue",
    value: function __getValue() {
      var _data = this._data;

      var data = _objectWithoutProperties(_data, []);

      var contextChildren = _data.contextChildren;
      var width = _data.width;
      var height = _data.height;
      var children = _data.children;
      var uniforms = _data.uniforms;

      data.width = isAnimated(width) ? width.__getValue() : width;
      data.height = isAnimated(height) ? height.__getValue() : height;
      data.contextChildren = contextChildren.map(function (c) {
        return c.__getValue();
      });
      data.children = children.map(function (c) {
        return c.__getValue();
      });
      data.uniforms = uniforms.__getValue();
      return data;
    }
  }, {
    key: "__attach",
    value: function __attach() {
      var _this4 = this;

      var _data2 = this._data;
      var contextChildren = _data2.contextChildren;
      var children = _data2.children;
      var uniforms = _data2.uniforms;
      var width = _data2.width;
      var height = _data2.height;

      if (isAnimated(width)) width.__addChild(this);
      if (isAnimated(height)) height.__addChild(this);
      contextChildren.forEach(function (c) {
        return c.__addChild(_this4);
      });
      children.forEach(function (c) {
        return c.__addChild(_this4);
      });
      uniforms.__addChild(this);
    }
  }, {
    key: "__detach",
    value: function __detach() {
      var _this5 = this;

      var _data3 = this._data;
      var contextChildren = _data3.contextChildren;
      var children = _data3.children;
      var uniforms = _data3.uniforms;
      var width = _data3.width;
      var height = _data3.height;

      if (isAnimated(width)) width.__removeChild(this);
      if (isAnimated(height)) height.__removeChild(this);
      contextChildren.forEach(function (c) {
        return c.__removeChild(_this5);
      });
      children.forEach(function (c) {
        return c.__removeChild(_this5);
      });
      uniforms.__removeChild(this);
    }
  }]);

  return AnimatedData;
}(AnimatedWithChildren);

module.exports = AnimatedData;

},{"./isAnimated":23}],2:[function(require,module,exports){
(function (global){
"use strict";

var _createClass = function () { function defineProperties(target, props) { for (var i = 0; i < props.length; i++) { var descriptor = props[i]; descriptor.enumerable = descriptor.enumerable || false; descriptor.configurable = true; if ("value" in descriptor) descriptor.writable = true; Object.defineProperty(target, descriptor.key, descriptor); } } return function (Constructor, protoProps, staticProps) { if (protoProps) defineProperties(Constructor.prototype, protoProps); if (staticProps) defineProperties(Constructor, staticProps); return Constructor; }; }();

function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError("Cannot call a class as a function"); } }

function _possibleConstructorReturn(self, call) { if (!self) { throw new ReferenceError("this hasn't been initialised - super() hasn't been called"); } return call && (typeof call === "object" || typeof call === "function") ? call : self; }

function _inherits(subClass, superClass) { if (typeof superClass !== "function" && superClass !== null) { throw new TypeError("Super expression must either be null or a function, not " + typeof superClass); } subClass.prototype = Object.create(superClass && superClass.prototype, { constructor: { value: subClass, enumerable: false, writable: true, configurable: true } }); if (superClass) Object.setPrototypeOf ? Object.setPrototypeOf(subClass, superClass) : subClass.__proto__ = superClass; }

var React = (typeof window !== "undefined" ? window['React'] : typeof global !== "undefined" ? global['React'] : null);
var Component = React.Component;
var PropTypes = React.PropTypes;

var invariant = require("invariant");

var Node = function (_Component) {
  _inherits(Node, _Component);

  function Node() {
    _classCallCheck(this, Node);

    return _possibleConstructorReturn(this, (Node.__proto__ || Object.getPrototypeOf(Node)).apply(this, arguments));
  }

  _createClass(Node, [{
    key: "render",
    value: function render() {
      invariant(false, "GL.Node elements can only be used as children of GL.Surface / GL.Node and should not be rendered");
    }
  }]);

  return Node;
}(Component);

Node.isGLNode = true;

Node.displayName = "GL.Node";

Node.propTypes = {
  shader: PropTypes.any.isRequired,
  uniforms: PropTypes.object,
  children: PropTypes.node,
  width: PropTypes.any,
  height: PropTypes.any,
  preload: PropTypes.bool
};

module.exports = Node;

}).call(this,typeof global !== "undefined" ? global : typeof self !== "undefined" ? self : typeof window !== "undefined" ? window : {})
},{"invariant":27}],3:[function(require,module,exports){
"use strict";

var _typeof = typeof Symbol === "function" && typeof Symbol.iterator === "symbol" ? function (obj) { return typeof obj; } : function (obj) { return obj && typeof Symbol === "function" && obj.constructor === Symbol ? "symbol" : typeof obj; };

var _extends = Object.assign || function (target) { for (var i = 1; i < arguments.length; i++) { var source = arguments[i]; for (var key in source) { if (Object.prototype.hasOwnProperty.call(source, key)) { target[key] = source[key]; } } } return target; };

var invariant = require("invariant");
var defer = require("promise-defer");

var INLINE_NAME = "<inline>";

var _uid = 1;
var names = {}; // keep names
var shaders = {}; // keep shader objects
var shadersCompileResponses = {}; // keep promise of compile responses
var shadersCompileResults = {}; // keep only the successful result
var shadersReferenceCounters = {}; // reference count the shaders created with Shaders.create()/used inline so we don't delete them if one of 2 dups is still used

var surfaceInlines = {};
var previousSurfaceInlines = {};

var implDefer = defer();
var implementation = implDefer.promise;

var add = function add(shader) {
  var existingId = findShaderId(shaders, shader);
  var id = existingId || _uid++;
  var promise = void 0;
  if (!existingId) {
    names[id] = shader.name;
    shaders[id] = shader;
    shadersReferenceCounters[id] = 0;
    shadersCompileResponses[id] = promise = implementation.then(function (impl) {
      return impl.add(id, shader);
    }).then(function (result) {
      return shadersCompileResults[id] = result;
    });
  } else {
    promise = shadersCompileResponses[id];
  }
  return { id: id, promise: promise };
};

var remove = function remove(id) {
  delete shaders[id];
  delete names[id];
  delete shadersReferenceCounters[id];
  delete shadersCompileResponses[id];
  implementation.then(function (impl) {
    return impl.remove(id);
  });
};

var getShadersToRemove = function getShadersToRemove() {
  return Object.keys(shadersReferenceCounters).filter(function (id) {
    return shadersReferenceCounters[id] <= 0;
  }).map(function (id) {
    return parseInt(id, 10);
  });
};

var scheduled = void 0;
var gcNow = function gcNow() {
  clearTimeout(scheduled);
  getShadersToRemove().forEach(remove);
};
var scheduleGC = function scheduleGC() {
  // debounce the shader deletion to let a last chance to a future dup shader to appear
  // the idea is also to postpone this operation when the app is not so busy
  var noDebounce = getShadersToRemove().length > 20;
  if (!noDebounce) clearTimeout(scheduled);
  scheduled = setTimeout(gcNow, 500);
};

var sameShader = function sameShader(a, b) {
  return a.frag === b.frag;
};

var findShaderId = function findShaderId(shaders, shader) {
  for (var id in shaders) {
    if (sameShader(shaders[id], shader)) {
      return parseInt(id, 10);
    }
  }
  return null;
};

var logError = function logError(shader) {
  return function (error) {
    return console.error( //eslint-disable-line no-console
    "Shader '" + shader.name + "' failed to compile:\n" + error);
  };
};

var Shaders = {
  _onSurfaceWillMount: function _onSurfaceWillMount(surfaceId) {
    surfaceInlines[surfaceId] = [];
  },
  _onSurfaceWillUnmount: function _onSurfaceWillUnmount(surfaceId) {
    surfaceInlines[surfaceId].forEach(function (id) {
      return shadersReferenceCounters[id]--;
    });
    delete surfaceInlines[surfaceId];
    delete previousSurfaceInlines[surfaceId];
    scheduleGC();
  },
  _beforeSurfaceBuild: function _beforeSurfaceBuild(surfaceId) {
    previousSurfaceInlines[surfaceId] = surfaceInlines[surfaceId];
    surfaceInlines[surfaceId] = [];
  },


  // Resolve the shader field of GL.Node.
  // it can be an id (created with Shaders.create) or an inline object.
  _resolve: function _resolve(idOrObject, surfaceId, compileHandler) {
    if (typeof idOrObject === "number") return idOrObject;

    var _add = add(_extends({ name: INLINE_NAME }, idOrObject));

    var id = _add.id;
    var promise = _add.promise;

    if (compileHandler) {
      promise.then(function (result) {
        return compileHandler(null, result);
      }, function (error) {
        return compileHandler(error);
      });
    } else {
      promise.catch(logError(Shaders.get(id)));
    }
    var inlines = surfaceInlines[surfaceId];
    inlines.push(id);
    return id;
  },
  _afterSurfaceBuild: function _afterSurfaceBuild(surfaceId) {
    previousSurfaceInlines[surfaceId].forEach(function (id) {
      return shadersReferenceCounters[id]--;
    });
    surfaceInlines[surfaceId].forEach(function (id) {
      return shadersReferenceCounters[id]++;
    });
    delete previousSurfaceInlines[surfaceId];
    scheduleGC();
  },


  //~~~ Exposed methods ~~~ //

  // Create shaders statically
  create: function create(obj, onAllCompile) {
    invariant((typeof obj === "undefined" ? "undefined" : _typeof(obj)) === "object", "config must be an object");
    var result = {};
    var compileErrors = {},
        compileResults = {};
    Promise.all(Object.keys(obj).map(function (key) {
      var shader = obj[key];
      invariant((typeof shader === "undefined" ? "undefined" : _typeof(shader)) === "object" && typeof shader.frag === "string", "invalid shader given to Shaders.create(). A valid shader is a { frag: String }");

      var _add2 = add(_extends({ name: key }, shader));

      var id = _add2.id;
      var promise = _add2.promise;

      result[key] = id;
      shadersReferenceCounters[id]++;
      return promise.then(function (result) {
        return compileResults[key] = result;
      }, function (error) {
        return compileErrors[key] = error;
      });
    })).then(function () {
      if (onAllCompile) {
        onAllCompile(Object.keys(compileErrors).length ? compileErrors : null, compileResults);
      } else {
        Object.keys(compileErrors).forEach(function (key) {
          return logError(Shaders.get(result[key]))(compileErrors[key]);
        });
      }
    });
    return result;
  },


  // Get the shader object by id.
  get: function get(id) {
    return Object.freeze(shaders[id]);
  },


  // Synchronously retrieve the successful compilation response.
  // returns or ShaderResult object or null if there were a failure or not ready
  getCompilationResult: function getCompilationResult(id) {
    return shadersCompileResults[id] || null;
  },


  // Get the promise of the compilation state. Allows you to wait for compilation
  // and also map on errors.
  // Returns null only if you never have created this shader.
  getCompilationPromise: function getCompilationPromise(id) {
    return shadersCompileResponses[id] || null;
  },


  // List all shader ids that exists at the moment.
  list: function list() {
    return Object.keys(shaders);
  },


  // Check if a shader exists
  exists: function exists(id) {
    return id in shaders;
  },


  gcNow: gcNow,

  setImplementation: function setImplementation(impl) {
    invariant(implDefer, "Shaders.setImplementation can be called only once");
    implDefer.resolve(impl);
    implDefer = null;
  },

  implementation: implementation
};

module.exports = Object.freeze(Shaders);

},{"invariant":27,"promise-defer":28}],4:[function(require,module,exports){
(function (global){
"use strict";

var _createClass = function () { function defineProperties(target, props) { for (var i = 0; i < props.length; i++) { var descriptor = props[i]; descriptor.enumerable = descriptor.enumerable || false; descriptor.configurable = true; if ("value" in descriptor) descriptor.writable = true; Object.defineProperty(target, descriptor.key, descriptor); } } return function (Constructor, protoProps, staticProps) { if (protoProps) defineProperties(Constructor.prototype, protoProps); if (staticProps) defineProperties(Constructor, staticProps); return Constructor; }; }();

function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError("Cannot call a class as a function"); } }

function _possibleConstructorReturn(self, call) { if (!self) { throw new ReferenceError("this hasn't been initialised - super() hasn't been called"); } return call && (typeof call === "object" || typeof call === "function") ? call : self; }

function _inherits(subClass, superClass) { if (typeof superClass !== "function" && superClass !== null) { throw new TypeError("Super expression must either be null or a function, not " + typeof superClass); } subClass.prototype = Object.create(superClass && superClass.prototype, { constructor: { value: subClass, enumerable: false, writable: true, configurable: true } }); if (superClass) Object.setPrototypeOf ? Object.setPrototypeOf(subClass, superClass) : subClass.__proto__ = superClass; }

var React = (typeof window !== "undefined" ? window['React'] : typeof global !== "undefined" ? global['React'] : null);
var Component = React.Component;
var PropTypes = React.PropTypes;

var invariant = require("invariant");

var Uniform = function (_Component) {
  _inherits(Uniform, _Component);

  function Uniform() {
    _classCallCheck(this, Uniform);

    return _possibleConstructorReturn(this, (Uniform.__proto__ || Object.getPrototypeOf(Uniform)).apply(this, arguments));
  }

  _createClass(Uniform, [{
    key: "render",
    value: function render() {
      invariant(false, "GL.Uniform elements are for GL.Node configuration only and should not be rendered");
    }
  }]);

  return Uniform;
}(Component);

Uniform.isGLUniform = true;

Uniform.displayName = "GL.Uniform";

Uniform.propTypes = {
  children: PropTypes.any.isRequired,
  name: PropTypes.string.isRequired
};

module.exports = Uniform;

}).call(this,typeof global !== "undefined" ? global : typeof self !== "undefined" ? self : typeof window !== "undefined" ? window : {})
},{"invariant":27}],5:[function(require,module,exports){
(function (global){
"use strict";

var _typeof = typeof Symbol === "function" && typeof Symbol.iterator === "symbol" ? function (obj) { return typeof obj; } : function (obj) { return obj && typeof Symbol === "function" && obj.constructor === Symbol ? "symbol" : typeof obj; };

var _extends = Object.assign || function (target) { for (var i = 1; i < arguments.length; i++) { var source = arguments[i]; for (var key in source) { if (Object.prototype.hasOwnProperty.call(source, key)) { target[key] = source[key]; } } } return target; };

var _createClass = function () { function defineProperties(target, props) { for (var i = 0; i < props.length; i++) { var descriptor = props[i]; descriptor.enumerable = descriptor.enumerable || false; descriptor.configurable = true; if ("value" in descriptor) descriptor.writable = true; Object.defineProperty(target, descriptor.key, descriptor); } } return function (Constructor, protoProps, staticProps) { if (protoProps) defineProperties(Constructor.prototype, protoProps); if (staticProps) defineProperties(Constructor, staticProps); return Constructor; }; }();

function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError("Cannot call a class as a function"); } }

function _possibleConstructorReturn(self, call) { if (!self) { throw new ReferenceError("this hasn't been initialised - super() hasn't been called"); } return call && (typeof call === "object" || typeof call === "function") ? call : self; }

function _inherits(subClass, superClass) { if (typeof superClass !== "function" && superClass !== null) { throw new TypeError("Super expression must either be null or a function, not " + typeof superClass); } subClass.prototype = Object.create(superClass && superClass.prototype, { constructor: { value: subClass, enumerable: false, writable: true, configurable: true } }); if (superClass) Object.setPrototypeOf ? Object.setPrototypeOf(subClass, superClass) : subClass.__proto__ = superClass; }

var React = (typeof window !== "undefined" ? window['React'] : typeof global !== "undefined" ? global['React'] : null);
var invariant = require("invariant");

module.exports = function createComponent(renderGLNode, staticFields) {

  invariant(typeof renderGLNode === "function", "GL.createComponent(props => glnode) must have a function in parameter");

  var GLComponent = function (_React$Component) {
    _inherits(GLComponent, _React$Component);

    function GLComponent() {
      _classCallCheck(this, GLComponent);

      return _possibleConstructorReturn(this, (GLComponent.__proto__ || Object.getPrototypeOf(GLComponent)).apply(this, arguments));
    }

    _createClass(GLComponent, [{
      key: "render",
      value: function render() {
        var glNode = renderGLNode(_extends({}, this.context, this.props));

        invariant(glNode && glNode.type && (glNode.type.isGLNode || glNode.type.isGLComponent), "%s: The GL.createComponent function parameter must return a GL.Node or " + "another GL Component", GLComponent.displayName);

        return glNode;
      }
    }]);

    return GLComponent;
  }(React.Component);

  GLComponent.isGLComponent = true;

  GLComponent.displayName = renderGLNode.name || "";

  if (staticFields) {
    invariant((typeof staticFields === "undefined" ? "undefined" : _typeof(staticFields)) === "object", "second parameter of createComponent must be an object of static fields " + "to set in the React component. (example: propTypes, displayName)");

    for (var key in staticFields) {
      GLComponent[key] = staticFields[key];
    }
  }

  return GLComponent;
};

}).call(this,typeof global !== "undefined" ? global : typeof self !== "undefined" ? self : typeof window !== "undefined" ? window : {})
},{"invariant":27}],6:[function(require,module,exports){
(function (process,global){
"use strict";

var _extends = Object.assign || function (target) { for (var i = 1; i < arguments.length; i++) { var source = arguments[i]; for (var key in source) { if (Object.prototype.hasOwnProperty.call(source, key)) { target[key] = source[key]; } } } return target; };

var _createClass = function () { function defineProperties(target, props) { for (var i = 0; i < props.length; i++) { var descriptor = props[i]; descriptor.enumerable = descriptor.enumerable || false; descriptor.configurable = true; if ("value" in descriptor) descriptor.writable = true; Object.defineProperty(target, descriptor.key, descriptor); } } return function (Constructor, protoProps, staticProps) { if (protoProps) defineProperties(Constructor.prototype, protoProps); if (staticProps) defineProperties(Constructor, staticProps); return Constructor; }; }();

function _objectWithoutProperties(obj, keys) { var target = {}; for (var i in obj) { if (keys.indexOf(i) >= 0) continue; if (!Object.prototype.hasOwnProperty.call(obj, i)) continue; target[i] = obj[i]; } return target; }

function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError("Cannot call a class as a function"); } }

function _possibleConstructorReturn(self, call) { if (!self) { throw new ReferenceError("this hasn't been initialised - super() hasn't been called"); } return call && (typeof call === "object" || typeof call === "function") ? call : self; }

function _inherits(subClass, superClass) { if (typeof superClass !== "function" && superClass !== null) { throw new TypeError("Super expression must either be null or a function, not " + typeof superClass); } subClass.prototype = Object.create(superClass && superClass.prototype, { constructor: { value: subClass, enumerable: false, writable: true, configurable: true } }); if (superClass) Object.setPrototypeOf ? Object.setPrototypeOf(subClass, superClass) : subClass.__proto__ = superClass; }

var React = (typeof window !== "undefined" ? window['React'] : typeof global !== "undefined" ? global['React'] : null);
var Component = React.Component;
var PropTypes = React.PropTypes;

var invariant = require("invariant");

var _require = require("./data");

var fill = _require.fill;
var resolve = _require.resolve;
var build = _require.build;

var Shaders = require("./Shaders");
var Node = require("./Node");
var postShader = require("./postShader");
var findGLNodeInGLComponentChildren = require("./data/findGLNodeInGLComponentChildren");
var invariantStrictPositive = require("./data/invariantStrictPositive");
var AnimatedData = require("./AnimatedData");
var runtime = require("./runtime");

var _glSurfaceId = 1;

function logResult(data, contentsVDOM) {
  if (typeof console !== "undefined" && console.debug // eslint-disable-line
  ) {
      console.debug("GL.Surface rendered with", data, contentsVDOM); // eslint-disable-line no-console
    }
}

module.exports = function (renderVcontainer, renderVcontent, renderVGL, getPixelRatio) {
  var _getGLCanvas = arguments.length <= 4 || arguments[4] === undefined ? function (glSurface) {
    return glSurface.refs.canvas;
  } : arguments[4];

  var GLSurface = function (_Component) {
    _inherits(GLSurface, _Component);

    function GLSurface(props, context) {
      _classCallCheck(this, GLSurface);

      var _this = _possibleConstructorReturn(this, (GLSurface.__proto__ || Object.getPrototypeOf(GLSurface)).call(this, props, context));

      _this._renderId = 0;
      _this._id = _glSurfaceId++;
      return _this;
    }

    _createClass(GLSurface, [{
      key: "componentWillMount",
      value: function componentWillMount() {
        Shaders._onSurfaceWillMount(this._id);
        this._build(this.props);
        this._attach();
      }
    }, {
      key: "componentWillUnmount",
      value: function componentWillUnmount() {
        this._renderId = 0;
        Shaders._onSurfaceWillUnmount(this._id);
        this._dataAnimated && this._dataAnimated.__detach();
      }
    }, {
      key: "componentWillReceiveProps",
      value: function componentWillReceiveProps(nextProps) {
        this._build(nextProps);
        this._attach();
      }
    }, {
      key: "_build",
      value: function _build(props) {
        var _this2 = this;

        var id = this._id;
        var renderId = ++this._renderId;
        var width = props.width;
        var height = props.height;
        var pixelRatioProps = props.pixelRatio;
        var children = props.children;
        var debug = props.debug;
        var preload = props.preload;


        invariant(children, "GL.Surface must have in children a GL.Node or a GL Component");

        var decorateOnShaderCompile = function decorateOnShaderCompile(onShaderCompile) {
          return onShaderCompile && // only decorated if onShaderCompile is defined
          function (error, result) {
            return renderId === _this2._renderId && // it's outdated. skip the callback call
            onShaderCompile(error, result);
          };
        }; // it's current. propagate the call

        var pixelRatio = pixelRatioProps || getPixelRatio(props);

        invariantStrictPositive(pixelRatio, "GL.Surface: pixelRatio prop");

        var surfaceContext = {
          width: width,
          height: height,
          pixelRatio: pixelRatio
        };

        var glNode = findGLNodeInGLComponentChildren(React.createElement(Node, _extends({
          shader: postShader
        }, surfaceContext, {
          uniforms: { t: children }
        })), surfaceContext);

        invariant(glNode && glNode.childGLNode, "GL.Surface must have in children a GL.Node or a GL Component");

        var via = glNode.via;
        var childGLNode = glNode.childGLNode;
        var context = glNode.context;


        var resolved = void 0;
        try {
          Shaders._beforeSurfaceBuild(id);
          resolved = resolve(fill(build(childGLNode, context, preload, via, id, decorateOnShaderCompile)));
        } catch (e) {
          throw e;
        } finally {
          Shaders._afterSurfaceBuild(id);
        }

        this._resolved = resolved;
        this._pixelRatio = pixelRatio;

        if (debug) logResult(resolved.data, resolved.contentsVDOM);
      }
    }, {
      key: "_attach",
      value: function _attach() {
        var _this3 = this;

        var oldDataAnimated = this._dataAnimated;
        var callback = function callback() {
          var canvas = _this3.getGLCanvas();
          if (!canvas) return;
          if (canvas.setNativeProps) {
            var data = _this3._dataAnimated.__getValue();
            canvas.setNativeProps({ data: data });
          } else {
            _this3.forceUpdate();
          }
        };
        this._dataAnimated = new AnimatedData(this._resolved.data, callback);

        oldDataAnimated && oldDataAnimated.__detach();
      }
    }, {
      key: "getGLCanvas",
      value: function getGLCanvas() {
        return _getGLCanvas(this);
      }
    }, {
      key: "captureFrame",
      value: function captureFrame() {
        var c = this.getGLCanvas();
        invariant(c, "c is '%s'. Is the component unmounted?", c);
        invariant(c.captureFrame, "captureFrame() should be implemented by GLCanvas");
        return c.captureFrame.apply(c, arguments);
      }
    }, {
      key: "render",
      value: function render() {
        var renderId = this._renderId;
        var _resolved = this._resolved;
        var contentsVDOM = _resolved.contentsVDOM;
        var imagesToPreload = _resolved.imagesToPreload;

        var data = this._dataAnimated.__getValue();
        var pixelRatio = this._pixelRatio;
        var props = this.props;
        var children = props.children;
        var debug = props.debug;
        var preload = props.preload;
        var style = props.style;
        var width = props.width;
        var height = props.height;
        var backgroundColor = props.backgroundColor;
        var visibleContent = props.visibleContent;
        var eventsThrough = props.eventsThrough;

        var restProps = _objectWithoutProperties(props, ["children", "debug", "preload", "style", "width", "height", "backgroundColor", "visibleContent", "eventsThrough"]);

        if (process.env.NODE_ENV !== "production") {
          var withoutKeys = contentsVDOM.filter(function (c) {
            return !c.key;
          });
          if (withoutKeys.length > 0) {
            console.warn("gl-react: To avoid potential remounting, please define a `key` prop on your contents:\n\n" + withoutKeys.map(function (c) {
              return "<" + (c.type.name || c.type.displayName || "unknown") + " key=??? ... />";
            }).join("\n") + "\n");
          }
        }

        return renderVcontainer({ width: width, height: height, style: style, visibleContent: visibleContent, eventsThrough: eventsThrough }, contentsVDOM.map(function (vdom, i) {
          return renderVcontent(data.width, data.height, vdom.key || i, runtime.decorateVDOMContent(vdom), { visibleContent: visibleContent });
        }), renderVGL(_extends({}, restProps, { // eslint-disable-line no-undef
          style: { backgroundColor: backgroundColor },
          width: width,
          height: height,
          pixelRatio: pixelRatio,
          data: data,
          nbContentTextures: contentsVDOM.length,
          imagesToPreload: imagesToPreload,
          renderId: renderId,
          visibleContent: visibleContent,
          eventsThrough: eventsThrough
        })));
      }
    }]);

    return GLSurface;
  }(Component);

  GLSurface.displayName = "GL.Surface";

  GLSurface.propTypes = {
    width: PropTypes.any.isRequired,
    height: PropTypes.any.isRequired,
    backgroundColor: PropTypes.string,
    pixelRatio: PropTypes.number,
    children: PropTypes.element.isRequired,
    preload: PropTypes.bool,
    autoRedraw: PropTypes.bool,
    eventsThrough: PropTypes.bool,
    visibleContent: PropTypes.bool,
    onLoad: PropTypes.func,
    onProgress: PropTypes.func
  };

  GLSurface.defaultProps = {
    preload: false,
    autoRedraw: false,
    eventsThrough: false,
    visibleContent: false,
    backgroundColor: "#000"
  };

  return GLSurface;
};

}).call(this,require('_process'),typeof global !== "undefined" ? global : typeof self !== "undefined" ? self : typeof window !== "undefined" ? window : {})
},{"./AnimatedData":1,"./Node":2,"./Shaders":3,"./data":15,"./data/findGLNodeInGLComponentChildren":14,"./data/invariantStrictPositive":16,"./postShader":24,"./runtime":25,"_process":26,"invariant":27}],7:[function(require,module,exports){
"use strict";

var _extends = Object.assign || function (target) { for (var i = 1; i < arguments.length; i++) { var source = arguments[i]; for (var key in source) { if (Object.prototype.hasOwnProperty.call(source, key)) { target[key] = source[key]; } } } return target; };

function Content(id) {
  return { type: "content", id: id };
}

function NDArray(ndarray) {
  return { type: "ndarray", ndarray: ndarray };
}

function URI(obj) {
  return _extends({ type: "uri" }, obj);
}

function Framebuffer(id) {
  return { type: "fbo", id: id };
}

function withOpts(obj, opts) {
  return _extends({}, obj, { opts: opts });
}

module.exports = {
  Content: Content,
  NDArray: NDArray,
  URI: URI,
  Framebuffer: Framebuffer,
  withOpts: withOpts
};

},{}],8:[function(require,module,exports){
(function (process,global){
"use strict";

var _typeof = typeof Symbol === "function" && typeof Symbol.iterator === "symbol" ? function (obj) { return typeof obj; } : function (obj) { return obj && typeof Symbol === "function" && obj.constructor === Symbol ? "symbol" : typeof obj; };

var _extends = Object.assign || function (target) { for (var i = 1; i < arguments.length; i++) { var source = arguments[i]; for (var key in source) { if (Object.prototype.hasOwnProperty.call(source, key)) { target[key] = source[key]; } } } return target; };

var _runtime = require("../runtime");

var _runtime2 = _interopRequireDefault(_runtime);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

function _objectWithoutProperties(obj, keys) { var target = {}; for (var i in obj) { if (keys.indexOf(i) >= 0) continue; if (!Object.prototype.hasOwnProperty.call(obj, i)) continue; target[i] = obj[i]; } return target; }

var React = (typeof window !== "undefined" ? window['React'] : typeof global !== "undefined" ? global['React'] : null);
var invariant = require("invariant");
var Uniform = require("../Uniform");
var Shaders = require("../Shaders");
var TextureObjects = require("./TextureObjects");
var duckTypeUniformValue = require("./duckTypeUniformValue");
var findGLNodeInGLComponentChildren = require("./findGLNodeInGLComponentChildren");
var invariantStrictPositive = require("./invariantStrictPositive");


//// build: converts the gl-react VDOM DSL into an internal data tree.

module.exports = function build(glNode, context, parentPreload, via, surfaceId, decorateOnShaderCompile) {
  var props = glNode.props;
  var shader = Shaders._resolve(props.shader, surfaceId, decorateOnShaderCompile(props.onShaderCompile));
  var glNodeUniforms = props.uniforms;

  var _context$props = _extends({}, context, props);

  var width = _context$props.width;
  var height = _context$props.height;
  var pixelRatio = _context$props.pixelRatio;

  var newContext = {
    width: width,
    height: height,
    pixelRatio: pixelRatio
  };
  var glNodeChildren = props.children;
  var preload = "preload" in props ? props.preload : parentPreload;

  invariant(Shaders.exists(shader), "Shader #%s does not exists", shader);

  var shaderName = Shaders.get(shader).name;
  invariantStrictPositive(pixelRatio, "GL Component (" + shaderName + "). pixelRatio prop");

  var uniforms = _extends({}, glNodeUniforms);
  var children = [];
  var contents = [];

  React.Children.forEach(glNodeChildren, function (child) {
    invariant(child.type === Uniform, "(Shader '%s') GL.Node can only contains children of type GL.Uniform. Got '%s'", shaderName, child.type && child.type.displayName || child);
    var _child$props = child.props;
    var name = _child$props.name;
    var children = _child$props.children;

    var opts = _objectWithoutProperties(_child$props, ["name", "children"]);

    invariant(typeof name === "string" && name, "(Shader '%s') GL.Uniform must define an name String", shaderName);
    invariant(!glNodeUniforms || !(name in glNodeUniforms), "(Shader '%s') The uniform '%s' set by GL.Uniform must not be in {uniforms} props", shaderName);
    invariant(!(name in uniforms), "(Shader '%s') The uniform '%s' set by GL.Uniform must not be defined in another GL.Uniform", shaderName);
    uniforms[name] = !children || children.value ? children : { value: children, opts: opts }; // eslint-disable-line no-undef
  });

  Object.keys(uniforms).forEach(function (name) {
    var value = uniforms[name],
        opts = void 0;
    if (value && (typeof value === "undefined" ? "undefined" : _typeof(value)) === "object" && !value.prototype && "value" in value) {
      // if value has a value field, we tread this field as the value, but keep opts in memory if provided
      if (_typeof(value.opts) === "object") {
        opts = value.opts;
      }
      value = value.value;
    }

    value = _runtime2.default.decorateUniformValue(value);

    try {
      switch (duckTypeUniformValue(value)) {

        case "string":
          // uri specified as a string
          uniforms[name] = TextureObjects.withOpts(TextureObjects.URI({ uri: value }), opts);
          break;

        case "{uri}":
          // uri specified in an object, we keep all other fields for RN "local" image use-case
          uniforms[name] = TextureObjects.withOpts(TextureObjects.URI(value), opts);
          break;

        case "ndarray":
          uniforms[name] = TextureObjects.withOpts(TextureObjects.NDArray(value), opts);
          break;

        case "vdom[]":
        case "vdom":
          {
            var res = findGLNodeInGLComponentChildren(value, newContext);
            if (res) {
              var childGLNode = res.childGLNode;
              var _via = res.via;
              var _context = res.context;
              // We have found a GL.Node children, we integrate it in the tree and recursively do the same

              children.push({
                vdom: value,
                uniform: name,
                data: build(childGLNode, _context, preload, _via, surfaceId, decorateOnShaderCompile)
              });
            } else {
              // in other cases VDOM, we will use child as a content
              contents.push({
                vdom: value,
                uniform: name,
                opts: opts
              });
            }
            break;
          }

        default:
          // Remaining cases will just set the value without further transformation
          uniforms[name] = value;
      }
    } catch (e) {
      delete uniforms[name];
      var message = "Shader '" + shaderName + "': uniform '" + name + "' " + e.message;
      if (process.env.NODE_ENV !== "production") console.error(message, value); // eslint-disable-line no-console
      throw new Error(message);
    }
  });

  return {
    shader: shader,
    uniforms: uniforms,
    width: width,
    height: height,
    pixelRatio: pixelRatio,
    children: children,
    contents: contents,
    preload: preload,
    via: via
  };
};

}).call(this,require('_process'),typeof global !== "undefined" ? global : typeof self !== "undefined" ? self : typeof window !== "undefined" ? window : {})
},{"../Shaders":3,"../Uniform":4,"../runtime":25,"./TextureObjects":7,"./duckTypeUniformValue":9,"./findGLNodeInGLComponentChildren":14,"./invariantStrictPositive":16,"_process":26,"invariant":27}],9:[function(require,module,exports){
(function (global){
"use strict";

var _typeof = typeof Symbol === "function" && typeof Symbol.iterator === "symbol" ? function (obj) { return typeof obj; } : function (obj) { return obj && typeof Symbol === "function" && obj.constructor === Symbol ? "symbol" : typeof obj; };

var React = (typeof window !== "undefined" ? window['React'] : typeof global !== "undefined" ? global['React'] : null);
var isAnimated = require("../isAnimated");

// infer the uniform value type and validate it (throw error if invalid)

function duckTypeUniformValue(obj) {
  var typ = typeof obj === "undefined" ? "undefined" : _typeof(obj);

  if (typ === "number") {
    if (isNaN(obj) || !isFinite(obj)) throw new Error("invalid number: '" + obj + "'");
    return typ;
  }

  if (typ === "boolean") {
    return typ;
  }

  if (typ === "string") {
    return typ;
  }

  if (typ === "undefined") {
    return null;
  }

  if (typ === "object") {

    if (!obj) {
      return null;
    }

    if (typeof obj.uri === "string") {
      return "{uri}";
    }

    if (obj.data && obj.shape && obj.stride) {
      return "ndarray";
    }

    if (obj instanceof Array) {
      var length = obj.length;
      if (!length) throw new Error("array is empty");
      var foundAnimated = false;
      var foundVDOM = false;
      var foundNumber = false;
      var foundBoolean = false;
      for (var i = 0; i < length; i++) {
        var val = obj[i];
        var t = typeof val === "undefined" ? "undefined" : _typeof(val);
        switch (t) {
          case "object":
            if (val && isAnimated(val)) foundAnimated = true;else if (val && React.isValidElement(val)) foundVDOM = true;else if (val instanceof Array) return duckTypeUniformValue(val);else throw new Error("at index " + i + ", Unrecognized object: '" + val + "'");
            break;

          case "number":
            if (isNaN(val) || !isFinite(val)) throw new Error("at index " + i + ", invalid number: '" + val + "'");
            foundNumber = true;
            break;

          case "boolean":
            foundBoolean = true;
            break;

          default:
            throw new Error("at index " + i + ", Unrecognized object: " + val);
        }
      }

      var foundNumberOrBooleanOrAnimated = foundNumber || foundBoolean || foundAnimated;
      if (foundNumberOrBooleanOrAnimated && foundVDOM) {
        throw new Error("Invalid array. Found both VDOM value and numbers/booleans/animated");
      }

      if (foundVDOM) {
        return "vdom[]";
      }
      if (foundAnimated) {
        return "animated[]";
      }
      if (foundNumber) {
        return "number[]";
      }
      if (foundBoolean) {
        return "boolean[]";
      }
    }

    if (isAnimated(obj)) {
      return "animated";
    }

    if (React.isValidElement(obj)) {
      return "vdom";
    }
  }

  throw new Error("Unrecognized object: " + obj);
}

module.exports = duckTypeUniformValue;

}).call(this,typeof global !== "undefined" ? global : typeof self !== "undefined" ? self : typeof window !== "undefined" ? window : {})
},{"../isAnimated":23}],10:[function(require,module,exports){
"use strict";

var _typeof = typeof Symbol === "function" && typeof Symbol.iterator === "symbol" ? function (obj) { return typeof obj; } : function (obj) { return obj && typeof Symbol === "function" && obj.constructor === Symbol ? "symbol" : typeof obj; };

function extractImages(uniforms) {
  var images = [];
  for (var u in uniforms) {
    var value = uniforms[u];
    if (value && (typeof value === "undefined" ? "undefined" : _typeof(value)) === "object" && value.type === "uri" && value.uri && typeof value.uri === "string") {
      images.push(value);
    }
  }
  return images;
}

module.exports = extractImages;

},{}],11:[function(require,module,exports){
"use strict";

var _extends = Object.assign || function (target) { for (var i = 1; i < arguments.length; i++) { var source = arguments[i]; for (var key in source) { if (Object.prototype.hasOwnProperty.call(source, key)) { target[key] = source[key]; } } } return target; };

// fill the result of build() with more information that will make resolve() more efficient

function fill(dataTree) {
  function fillRec(node) {
    // we compute all the descendants vdom under the current node
    var descendantsVDOM = [node.vdom],
        descendantsVDOMData = [node.data];
    var newChildren = node.data.children.map(function (node) {
      var res = fillRec(node);
      if (descendantsVDOM.indexOf(res.vdom) === -1) {
        descendantsVDOM.push(res.vdom);
        descendantsVDOMData.push(res.data);
      }
      res.descendantsVDOM.forEach(function (vdom, i) {
        if (descendantsVDOM.indexOf(vdom) === -1) {
          descendantsVDOM.push(vdom);
          descendantsVDOMData.push(res.descendantsVDOMData[i]);
        }
      });
      return res;
    });
    return _extends({}, node, {
      data: _extends({}, node.data, { children: newChildren }),
      descendantsVDOM: descendantsVDOM,
      descendantsVDOMData: descendantsVDOMData
    });
  }
  return fillRec({ data: dataTree }).data;
}

module.exports = fill;

},{}],12:[function(require,module,exports){
"use strict";

// recursively find shared VDOM across direct children.
// if a VDOM is used in 2 different children, it means we can share its computation in contextChildren
function findChildrenDuplicates(data, toIgnore) {
  var descendantsVDOM = [];
  var descendantsVDOMData = [];
  data.children.map(function (child) {
    descendantsVDOM = descendantsVDOM.concat(child.descendantsVDOM);
    descendantsVDOMData = descendantsVDOMData.concat(child.descendantsVDOMData);
  });
  return descendantsVDOM.map(function (vdom, allIndex) {
    if (toIgnore.indexOf(vdom) !== -1) return;
    var occ = 0;
    for (var i = 0; i < data.children.length; i++) {
      if (data.children[i].descendantsVDOM.indexOf(vdom) !== -1) {
        occ++;
        if (occ > 1) return {
          data: descendantsVDOMData[allIndex],
          vdom: vdom
        };
      }
    }
  }).filter(function (obj) {
    return obj;
  });
}

module.exports = findChildrenDuplicates;

},{}],13:[function(require,module,exports){
"use strict";

// recursively find all contents but without duplicates by comparing VDOM reference
function findContentsUniq(data) {
  var vdoms = [];
  var contents = [];
  function rec(data) {
    data.contents.forEach(function (content) {
      if (vdoms.indexOf(content.vdom) === -1) {
        vdoms.push(content.vdom);
        contents.push(content);
      }
    });
    data.children.forEach(function (child) {
      rec(child.data);
    });
  }
  rec(data);
  return contents;
}

module.exports = findContentsUniq;

},{}],14:[function(require,module,exports){
"use strict";

var _extends = Object.assign || function (target) { for (var i = 1; i < arguments.length; i++) { var source = arguments[i]; for (var key in source) { if (Object.prototype.hasOwnProperty.call(source, key)) { target[key] = source[key]; } } } return target; };

var unfoldGLComponent = require("./unfoldGLComponent");
var pluckObject = require("./pluckObject");

module.exports = function findGLNodeInGLComponentChildren(children, context) {
  // going down the VDOM tree, while we can unfold GLComponent
  var via = [];
  var accContext = context;
  for (var c = children; c && typeof c.type === "function"; c = unfoldGLComponent(c, accContext, via)) {
    accContext = _extends({}, accContext, pluckObject(c.props, ["width", "height", "pixelRatio"]));
    if (c.type.isGLNode) return { childGLNode: c, via: via, context: accContext }; // found a GLNode
  }
};

},{"./pluckObject":18,"./unfoldGLComponent":20}],15:[function(require,module,exports){
"use strict";

module.exports = {
  build: require("./build"),
  fill: require("./fill"),
  resolve: require("./resolve")
};

},{"./build":8,"./fill":11,"./resolve":19}],16:[function(require,module,exports){
"use strict";

var invariant = require("invariant");

function invariantStrictPositive(value, name) {
  invariant(typeof value === "number" && value > 0 && !isNaN(value), "%s must be a strictly positive number. Got: '%s'", name, value);
}

module.exports = invariantStrictPositive;

},{"invariant":27}],17:[function(require,module,exports){
(function (global){
"use strict";

var React = (typeof window !== "undefined" ? window['React'] : typeof global !== "undefined" ? global['React'] : null);

module.exports = function pickReactFirstChild(children) {
  return React.Children.count(children) === 1 ? children instanceof Array ? children[0] : children : null;
};

}).call(this,typeof global !== "undefined" ? global : typeof self !== "undefined" ? self : typeof window !== "undefined" ? window : {})
},{}],18:[function(require,module,exports){
"use strict";

module.exports = function (object, keys) {
  var o = {};
  keys.forEach(function (k) {
    if (object.hasOwnProperty(k)) o[k] = object[k];
  });
  return o;
};

},{}],19:[function(require,module,exports){
"use strict";

var _extends = Object.assign || function (target) { for (var i = 1; i < arguments.length; i++) { var source = arguments[i]; for (var key in source) { if (Object.prototype.hasOwnProperty.call(source, key)) { target[key] = source[key]; } } } return target; };

function _objectWithoutProperties(obj, keys) { var target = {}; for (var i in obj) { if (keys.indexOf(i) >= 0) continue; if (!Object.prototype.hasOwnProperty.call(obj, i)) continue; target[i] = obj[i]; } return target; }

var invariant = require("invariant");

var findContentsUniq = require("./findContentsUniq");
var findChildrenDuplicates = require("./findChildrenDuplicates");
var TextureObjects = require("./TextureObjects");
var extractImages = require("./extractImages");
var uniqImages = require("./uniqImages");

///// resolve: takes the output of fill(build(*)) to generate the final data tree
// The algorithm simplifies the data tree to use shared framebuffers if some VDOM is duplicated in the tree (e.g: content / GL.Node)

function resolve(dataTree) {
  var imagesToPreload = [];

  // contents are view/canvas/image/video to be rasterized "globally"
  var contentsMeta = findContentsUniq(dataTree);
  var contentsVDOM = contentsMeta.map(function (_ref) {
    var vdom = _ref.vdom;
    return vdom;
  });

  // Recursively "resolve" the data to assign fboId and factorize duplicate uniforms to shared uniforms.
  function resolveRec(data, fboId, parentContext, parentFbos) {
    var dataUniforms = data.uniforms;
    var dataChildren = data.children;
    var dataContents = data.contents;
    var preload = data.preload;

    var dataRest = _objectWithoutProperties(data, ["uniforms", "children", "contents", "preload"]);

    var uniforms = _extends({}, dataUniforms);
    var parentContextVDOM = parentContext.map(function (_ref2) {
      var vdom = _ref2.vdom;
      return vdom;
    });

    // A function to generate a free FBO id for this node
    var genFboId = function (fboIdCounter) {
      return function () {
        fboIdCounter++;
        while (fboIdCounter === fboId || // fbo should not take the current one
        parentFbos.indexOf(fboIdCounter) !== -1) {
          // ensure fbo is not already taken in parents
          fboIdCounter++;
        }return fboIdCounter;
      };
    }(-1);

    // shared contains all nodes that are contains in more than one direct children.
    var shared = findChildrenDuplicates(data, parentContextVDOM);

    // We assign fboIds to shared
    var childrenContext = shared.map(function (_ref3) {
      var vdom = _ref3.vdom;

      var fboId = genFboId();
      return { vdom: vdom, fboId: fboId };
    });

    // We accumulate into context the childrenContext and the parentContext
    var context = parentContext.concat(childrenContext);
    var contextVDOM = context.map(function (_ref4) {
      var vdom = _ref4.vdom;
      return vdom;
    });
    var contextFbos = context.map(function (_ref5) {
      var fboId = _ref5.fboId;
      return fboId;
    });

    // contextChildren and children are field to fill for this node
    // We traverse the dataChildren to resolve where each child should go:
    // either we create a new child, a we create context child or we use an existing parent context
    var contextChildren = [];
    var children = [];

    var toRecord = dataChildren.concat(shared).map(function (child) {
      var uniform = child.uniform;
      var vdom = child.vdom;
      var data = child.data;

      var i = contextVDOM.indexOf(vdom);
      var fboId = void 0,
          addToCollection = void 0;
      if (i === -1) {
        fboId = genFboId();
        addToCollection = children;
      } else {
        fboId = context[i].fboId;
        if (i >= parentContext.length) {
          // is a new context children
          addToCollection = contextChildren;
        }
      }
      if (uniform) uniforms[uniform] = TextureObjects.Framebuffer(fboId);
      return { data: data, fboId: fboId, addToCollection: addToCollection };
    });

    var childrenFbos = toRecord.map(function (_ref6) {
      var fboId = _ref6.fboId;
      return fboId;
    });
    var allFbos = parentFbos.concat(contextFbos).concat(childrenFbos);

    var recorded = [];
    toRecord.forEach(function (_ref7) {
      var data = _ref7.data;
      var fboId = _ref7.fboId;
      var addToCollection = _ref7.addToCollection;

      if (recorded.indexOf(fboId) === -1) {
        recorded.push(fboId);
        if (addToCollection) addToCollection.unshift(resolveRec(data, fboId, context, allFbos));
      }
    });

    dataContents.forEach(function (_ref8) {
      var uniform = _ref8.uniform;
      var vdom = _ref8.vdom;
      var opts = _ref8.opts;

      var id = contentsVDOM.indexOf(vdom);
      invariant(id !== -1, "contents was discovered by findContentsMeta");
      uniforms[uniform] = TextureObjects.withOpts(TextureObjects.Content(id), opts);
    });

    // Check images to preload
    if (preload) {
      imagesToPreload = imagesToPreload.concat(extractImages(dataUniforms));
    }

    return _extends({}, dataRest, { // eslint-disable-line no-undef
      uniforms: uniforms,
      contextChildren: contextChildren,
      children: children,
      fboId: fboId
    });
  }

  return {
    data: resolveRec(dataTree, -1, [], []),
    contentsVDOM: contentsVDOM,
    imagesToPreload: uniqImages(imagesToPreload)
  };
}

module.exports = resolve;

},{"./TextureObjects":7,"./extractImages":10,"./findChildrenDuplicates":12,"./findContentsUniq":13,"./uniqImages":21,"invariant":27}],20:[function(require,module,exports){
"use strict";

var pickReactFirstChild = require("./pickReactFirstChild");

module.exports = function unfoldGLComponent(c, context, glComponentNameArray) {
  var Class = c.type;
  if (!Class.isGLComponent) return;
  var instance = new Class(); // FIXME: React might eventually improve to ease the work done here. see https://github.com/facebook/react/issues/4697#issuecomment-134335822
  instance.props = c.props;
  instance.context = context;
  var child = pickReactFirstChild(instance.render());
  var glComponentName = Class.displayName || Class.name || "";
  glComponentNameArray.push(glComponentName);
  return child;
};

},{"./pickReactFirstChild":17}],21:[function(require,module,exports){
"use strict";

function uniqImages(arr) {
  var uris = [];
  var coll = [];
  arr.forEach(function (item) {
    if (uris.indexOf(item.uri) === -1) {
      uris.push(item.uri);
      coll.push(item);
    }
  });
  return coll;
}

module.exports = uniqImages;

},{}],22:[function(require,module,exports){
"use strict";

var createComponent = require("./createComponent");
var createSurface = require("./createSurface");
var Node = require("./Node");
var Shaders = require("./Shaders");
var Uniform = require("./Uniform");
var runtime = require("./runtime");

module.exports = {
  createComponent: createComponent,
  createSurface: createSurface,
  Node: Node,
  Shaders: Shaders,
  Uniform: Uniform,
  runtime: runtime
};

},{"./Node":2,"./Shaders":3,"./Uniform":4,"./createComponent":5,"./createSurface":6,"./runtime":25}],23:[function(require,module,exports){
"use strict";

module.exports = function (o) {
  return o && o.__getValue;
};

},{}],24:[function(require,module,exports){
"use strict";

function _defineProperty(obj, key, value) { if (key in obj) { Object.defineProperty(obj, key, { value: value, enumerable: true, configurable: true, writable: true }); } else { obj[key] = value; } return obj; }

var Shaders = require("./Shaders");
var NAME = "(gl-react-post)";
module.exports = Shaders.create(_defineProperty({}, NAME, {
  frag: "\nprecision highp float;\nvarying vec2 uv;\nuniform sampler2D t;\nvoid main(){\n  gl_FragColor=texture2D(t,uv);\n}"
}))[NAME];

},{"./Shaders":3}],25:[function(require,module,exports){
"use strict";

module.exports = {
  decorateVDOMContent: function decorateVDOMContent(vdom) {
    return vdom;
  },
  decorateUniformValue: function decorateUniformValue(value /* name, shader */) {
    return value;
  }
};

},{}],26:[function(require,module,exports){
// shim for using process in browser
var process = module.exports = {};

// cached from whatever global is present so that test runners that stub it
// don't break things.  But we need to wrap it in a try catch in case it is
// wrapped in strict mode code which doesn't define any globals.  It's inside a
// function because try/catches deoptimize in certain engines.

var cachedSetTimeout;
var cachedClearTimeout;

function defaultSetTimout() {
    throw new Error('setTimeout has not been defined');
}
function defaultClearTimeout () {
    throw new Error('clearTimeout has not been defined');
}
(function () {
    try {
        if (typeof setTimeout === 'function') {
            cachedSetTimeout = setTimeout;
        } else {
            cachedSetTimeout = defaultSetTimout;
        }
    } catch (e) {
        cachedSetTimeout = defaultSetTimout;
    }
    try {
        if (typeof clearTimeout === 'function') {
            cachedClearTimeout = clearTimeout;
        } else {
            cachedClearTimeout = defaultClearTimeout;
        }
    } catch (e) {
        cachedClearTimeout = defaultClearTimeout;
    }
} ())
function runTimeout(fun) {
    if (cachedSetTimeout === setTimeout) {
        //normal enviroments in sane situations
        return setTimeout(fun, 0);
    }
    // if setTimeout wasn't available but was latter defined
    if ((cachedSetTimeout === defaultSetTimout || !cachedSetTimeout) && setTimeout) {
        cachedSetTimeout = setTimeout;
        return setTimeout(fun, 0);
    }
    try {
        // when when somebody has screwed with setTimeout but no I.E. maddness
        return cachedSetTimeout(fun, 0);
    } catch(e){
        try {
            // When we are in I.E. but the script has been evaled so I.E. doesn't trust the global object when called normally
            return cachedSetTimeout.call(null, fun, 0);
        } catch(e){
            // same as above but when it's a version of I.E. that must have the global object for 'this', hopfully our context correct otherwise it will throw a global error
            return cachedSetTimeout.call(this, fun, 0);
        }
    }


}
function runClearTimeout(marker) {
    if (cachedClearTimeout === clearTimeout) {
        //normal enviroments in sane situations
        return clearTimeout(marker);
    }
    // if clearTimeout wasn't available but was latter defined
    if ((cachedClearTimeout === defaultClearTimeout || !cachedClearTimeout) && clearTimeout) {
        cachedClearTimeout = clearTimeout;
        return clearTimeout(marker);
    }
    try {
        // when when somebody has screwed with setTimeout but no I.E. maddness
        return cachedClearTimeout(marker);
    } catch (e){
        try {
            // When we are in I.E. but the script has been evaled so I.E. doesn't  trust the global object when called normally
            return cachedClearTimeout.call(null, marker);
        } catch (e){
            // same as above but when it's a version of I.E. that must have the global object for 'this', hopfully our context correct otherwise it will throw a global error.
            // Some versions of I.E. have different rules for clearTimeout vs setTimeout
            return cachedClearTimeout.call(this, marker);
        }
    }



}
var queue = [];
var draining = false;
var currentQueue;
var queueIndex = -1;

function cleanUpNextTick() {
    if (!draining || !currentQueue) {
        return;
    }
    draining = false;
    if (currentQueue.length) {
        queue = currentQueue.concat(queue);
    } else {
        queueIndex = -1;
    }
    if (queue.length) {
        drainQueue();
    }
}

function drainQueue() {
    if (draining) {
        return;
    }
    var timeout = runTimeout(cleanUpNextTick);
    draining = true;

    var len = queue.length;
    while(len) {
        currentQueue = queue;
        queue = [];
        while (++queueIndex < len) {
            if (currentQueue) {
                currentQueue[queueIndex].run();
            }
        }
        queueIndex = -1;
        len = queue.length;
    }
    currentQueue = null;
    draining = false;
    runClearTimeout(timeout);
}

process.nextTick = function (fun) {
    var args = new Array(arguments.length - 1);
    if (arguments.length > 1) {
        for (var i = 1; i < arguments.length; i++) {
            args[i - 1] = arguments[i];
        }
    }
    queue.push(new Item(fun, args));
    if (queue.length === 1 && !draining) {
        runTimeout(drainQueue);
    }
};

// v8 likes predictible objects
function Item(fun, array) {
    this.fun = fun;
    this.array = array;
}
Item.prototype.run = function () {
    this.fun.apply(null, this.array);
};
process.title = 'browser';
process.browser = true;
process.env = {};
process.argv = [];
process.version = ''; // empty string to avoid regexp issues
process.versions = {};

function noop() {}

process.on = noop;
process.addListener = noop;
process.once = noop;
process.off = noop;
process.removeListener = noop;
process.removeAllListeners = noop;
process.emit = noop;

process.binding = function (name) {
    throw new Error('process.binding is not supported');
};

process.cwd = function () { return '/' };
process.chdir = function (dir) {
    throw new Error('process.chdir is not supported');
};
process.umask = function() { return 0; };

},{}],27:[function(require,module,exports){
(function (process){
/**
 * Copyright 2013-2015, Facebook, Inc.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */

'use strict';

/**
 * Use invariant() to assert state which your program assumes to be true.
 *
 * Provide sprintf-style format (only %s is supported) and arguments
 * to provide information about what broke and what you were
 * expecting.
 *
 * The invariant message will be stripped in production, but the invariant
 * will remain to ensure logic does not differ in production.
 */

var invariant = function(condition, format, a, b, c, d, e, f) {
  if (process.env.NODE_ENV !== 'production') {
    if (format === undefined) {
      throw new Error('invariant requires an error message argument');
    }
  }

  if (!condition) {
    var error;
    if (format === undefined) {
      error = new Error(
        'Minified exception occurred; use the non-minified dev environment ' +
        'for the full error message and additional helpful warnings.'
      );
    } else {
      var args = [a, b, c, d, e, f];
      var argIndex = 0;
      error = new Error(
        format.replace(/%s/g, function() { return args[argIndex++]; })
      );
      error.name = 'Invariant Violation';
    }

    error.framesToPop = 1; // we don't care about invariant's own frame
    throw error;
  }
};

module.exports = invariant;

}).call(this,require('_process'))
},{"_process":26}],28:[function(require,module,exports){
(function (global){
module.exports = Deferred
Deferred.defer = defer

function Deferred(Promise) {
  if (Promise == null) Promise = global.Promise
  if (this instanceof Deferred) return defer(Promise, this)
  else return defer(Promise, Object.create(Deferred.prototype))
}

function defer(Promise, deferred) {
  deferred.promise = new Promise(function(resolve, reject) {
    deferred.resolve = resolve
    deferred.reject = reject
  })

  return deferred
}

}).call(this,typeof global !== "undefined" ? global : typeof self !== "undefined" ? self : typeof window !== "undefined" ? window : {})
},{}]},{},[22])(22)
});